package ly.gmserver.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.ByteString;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import ly.db.entry.GmRuntimeScriptEntry;
import ly.db.entry.GmRuntimeScriptEntryHelper;
import ly.gmserver.service.GroovyRuntimeScriptCompiler.CompilationResult;
import ly.nacos.NacosService;
import ly.config.ServerTypeEnum;
import ly.proto.Cmd;
import ly.proto.GmRuntimeScriptProto;
import ly.rpc.RpcFailSavePolicy;
import ly.rpc.RpcUtils;
import ly.script.RuntimeScriptSignature;
import org.springframework.stereotype.Service;

/** Compiles, audits and directly pushes one-shot runtime scripts to one server. */
@Service
public class RuntimeScriptService {
  private static final long EXECUTION_TTL_MILLIS = 10 * 60 * 1000L;
  private static final int EXECUTION_TIMEOUT_MILLIS = 120_000;
  private static final int MAX_ARGUMENT_BYTES = 64 * 1024;
  private static final ObjectMapper MAPPER = new ObjectMapper();

  private final GroovyRuntimeScriptCompiler compiler;

  public RuntimeScriptService(GroovyRuntimeScriptCompiler compiler) {
    this.compiler = compiler;
  }

  public Map<String, Object> validate(String source, String entryClass) throws Exception {
    CompilationResult result = compiler.compile(source, entryClass);
    return Map.of(
        "sha256", result.sha256(),
        "bundleBytes", result.bundle().length,
        "classNames", result.classNames());
  }

  public GmRuntimeScriptEntry execute(
      String targetServerId,
      String source,
      String entryClass,
      String argumentsJson,
      String operator)
      throws Exception {
    validateInput(targetServerId, argumentsJson);
    if (source == null
        || source.getBytes(StandardCharsets.UTF_8).length
            > GroovyRuntimeScriptCompiler.MAX_SOURCE_BYTES) {
      throw new IllegalArgumentException(
          "Groovy 源码不能为空且不能超过 "
              + GroovyRuntimeScriptCompiler.MAX_SOURCE_BYTES
              + " 字节");
    }
    String executionId = "hotfix-" + UUID.randomUUID();
    GmRuntimeScriptEntry audit =
        createAudit(
            executionId, targetServerId, source, entryClass, argumentsJson, operator);
    try {
      CompilationResult compiled = compiler.compile(source, entryClass);
      audit.setClassSha256(compiled.sha256());
      audit.setCompileMessage(
          "编译成功: " + compiled.classNames().size() + " classes, " + compiled.bundle().length + " bytes");
      audit.setStatus("COMPILED");
      audit.setUpdateTime(LocalDateTime.now());
      GmRuntimeScriptEntryHelper.update(
          audit, "class_sha256", "compile_message", "status", "update_time");

      String normalizedArguments = normalizeArguments(argumentsJson);
      long expireAtMillis = System.currentTimeMillis() + EXECUTION_TTL_MILLIS;
      GmRuntimeScriptProto.csGmRuntimeScriptExecute.Builder requestBuilder =
          GmRuntimeScriptProto.csGmRuntimeScriptExecute.newBuilder()
              .setExecutionId(executionId)
              .setTargetServerId(targetServerId)
              .setEntryClass(entryClass)
              .setOperator(operator == null ? "" : operator)
              .setArgumentsJson(normalizedArguments)
              .setClassBundle(ByteString.copyFrom(compiled.bundle()))
              .setSha256(compiled.sha256())
              .setExpireAtMillis(expireAtMillis);
      String signature =
          RuntimeScriptSignature.sign(
              RuntimeScriptSignature.requireConfiguredSecret(),
              executionId,
              targetServerId,
              entryClass,
              operator == null ? "" : operator,
              normalizedArguments,
              compiled.sha256(),
              expireAtMillis);
      GmRuntimeScriptProto.csGmRuntimeScriptExecute request =
          requestBuilder.setSignature(signature).build();

      audit.setStatus("DISPATCHED");
      audit.setUpdateTime(LocalDateTime.now());
      GmRuntimeScriptEntryHelper.update(audit, "status", "update_time");

      GmRuntimeScriptProto.scGmRuntimeScriptExecute response =
          RpcUtils.syncRequestOrSaveOnFail(
              targetServerId,
              executionId.hashCode(),
              Cmd.CMD.CS_GmRuntimeScriptExecute_VALUE,
              request,
              EXECUTION_TIMEOUT_MILLIS,
              RpcFailSavePolicy.NONE);
      if (response == null) {
        throw new IllegalStateException("目标服务器无响应或执行超时: " + targetServerId);
      }
      audit.setStatus(response.getSuccess() ? "SUCCEEDED" : "FAILED");
      audit.setResultJson(auditResultJson(response));
      audit.setErrorMessage(
          response.getSuccess()
              ? response.getError()
              : firstNonBlank(response.getError(), response.getMessage()));
      audit.setElapsedMillis(response.getElapsedMillis());
      audit.setUpdateTime(LocalDateTime.now());
      GmRuntimeScriptEntryHelper.update(
          audit,
          "status",
          "result_json",
          "error_message",
          "elapsed_millis",
          "update_time");
      return audit;
    } catch (Exception error) {
      audit.setStatus("FAILED");
      audit.setErrorMessage(errorMessage(error));
      audit.setUpdateTime(LocalDateTime.now());
      GmRuntimeScriptEntryHelper.update(
          audit, "status", "error_message", "update_time");
      throw error;
    }
  }

  public List<Map<String, Object>> list() {
    return GmRuntimeScriptEntryHelper.listAll().stream()
        .sorted(
            Comparator.comparing(
                    GmRuntimeScriptEntry::getId,
                    Comparator.nullsLast(Comparator.reverseOrder())))
        .limit(100)
        .map(this::historyItem)
        .toList();
  }

  public List<Map<String, Object>> servers() {
    return NacosService.getInstance().getNodeMap().values().stream()
        .filter(node -> node.canUse())
        .filter(
            node ->
                node.getServerType() == ServerTypeEnum.GAME
                    || node.getServerType() == ServerTypeEnum.GATE)
        .filter(node -> !node.getServerId().equals(ly.ServerContext.getServerId()))
        .map(
            node ->
                Map.<String, Object>of(
                    "serverId", node.getServerId(),
                    "serverType", node.getServerType().getType(),
                    "ip", node.getIp(),
                    "port", node.getPort()))
        .sorted(Comparator.comparing(item -> String.valueOf(item.get("serverId"))))
        .toList();
  }

  private GmRuntimeScriptEntry createAudit(
      String executionId,
      String targetServerId,
      String source,
      String entryClass,
      String argumentsJson,
      String operator) {
    LocalDateTime now = LocalDateTime.now();
    GmRuntimeScriptEntry entry = new GmRuntimeScriptEntry();
    entry.setExecutionId(executionId);
    entry.setTargetServerId(targetServerId);
    entry.setEntryClass(entryClass);
    entry.setSourceCode(source);
    entry.setArgumentsJson(normalizeArguments(argumentsJson));
    entry.setClassSha256("");
    entry.setStatus("CREATED");
    entry.setCompileMessage("");
    entry.setResultJson("");
    entry.setErrorMessage("");
    entry.setOperator(operator == null ? "" : operator);
    entry.setElapsedMillis(0L);
    entry.setCreateTime(now);
    entry.setUpdateTime(now);
    if (!GmRuntimeScriptEntryHelper.save(entry)) {
      throw new IllegalStateException("保存脚本审计记录失败");
    }
    GmRuntimeScriptEntry saved = GmRuntimeScriptEntryHelper.getByExecutionId(executionId);
    if (saved == null) {
      throw new IllegalStateException("保存脚本审计记录后无法回读: " + executionId);
    }
    return saved;
  }

  private Map<String, Object> historyItem(GmRuntimeScriptEntry entry) {
    Map<String, Object> item = new java.util.LinkedHashMap<>();
    item.put("executionId", entry.getExecutionId());
    item.put("targetServerId", entry.getTargetServerId());
    item.put("entryClass", entry.getEntryClass());
    item.put("classSha256", entry.getClassSha256());
    item.put("status", entry.getStatus());
    item.put("compileMessage", entry.getCompileMessage());
    item.put("resultJson", entry.getResultJson());
    item.put("errorMessage", entry.getErrorMessage());
    item.put("operator", entry.getOperator());
    item.put("elapsedMillis", entry.getElapsedMillis());
    item.put("createTime", entry.getCreateTime());
    item.put("updateTime", entry.getUpdateTime());
    return item;
  }

  private void validateInput(String targetServerId, String argumentsJson) throws Exception {
    if (targetServerId == null || targetServerId.isBlank()) {
      throw new IllegalArgumentException("目标服务器不能为空");
    }
    boolean exists =
        NacosService.getInstance().getNodeMap().values().stream()
            .anyMatch(node -> node.canUse() && targetServerId.equals(node.getServerId()));
    if (!exists) {
      throw new IllegalArgumentException("目标服务器不存在或离线: " + targetServerId);
    }
    String normalizedArguments = normalizeArguments(argumentsJson);
    if (normalizedArguments.getBytes(StandardCharsets.UTF_8).length > MAX_ARGUMENT_BYTES) {
      throw new IllegalArgumentException("脚本参数超过 " + MAX_ARGUMENT_BYTES + " 字节");
    }
    MAPPER.readValue(normalizedArguments, new TypeReference<Map<String, Object>>() {});
  }

  private String normalizeArguments(String argumentsJson) {
    return argumentsJson == null || argumentsJson.isBlank() ? "{}" : argumentsJson.trim();
  }

  private String errorMessage(Throwable error) {
    String message = error.getMessage();
    return error.getClass().getName() + (message == null || message.isBlank() ? "" : ": " + message);
  }

  private String auditResultJson(GmRuntimeScriptProto.scGmRuntimeScriptExecute response)
      throws Exception {
    Object data =
        response.getResultJson().isBlank()
            ? Map.of()
            : MAPPER.readValue(response.getResultJson(), Object.class);
    return MAPPER.writeValueAsString(
        Map.of("message", response.getMessage(), "data", data));
  }

  private String firstNonBlank(String first, String second) {
    return first != null && !first.isBlank() ? first : (second == null ? "" : second);
  }
}
