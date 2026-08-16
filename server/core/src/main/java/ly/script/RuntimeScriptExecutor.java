package ly.script;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.lang.GroovySystem;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.HexFormat;
import java.util.Map;
import java.util.Set;
import ly.ServerContext;
import ly.proto.GmRuntimeScriptProto;

/** Executes a precompiled Groovy bundle once and releases every framework-held class reference. */
public final class RuntimeScriptExecutor {
  private static final int MAX_ARGUMENT_BYTES = 64 * 1024;
  private static final int MAX_EXECUTION_HISTORY = 1000;
  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final RuntimeScriptExecutor INSTANCE = new RuntimeScriptExecutor();

  private final Set<String> executionIds = new HashSet<>();
  private final ArrayDeque<String> executionOrder = new ArrayDeque<>();

  private RuntimeScriptExecutor() {}

  public static RuntimeScriptExecutor getInstance() {
    return INSTANCE;
  }

  public GmRuntimeScriptProto.scGmRuntimeScriptExecute execute(
      GmRuntimeScriptProto.csGmRuntimeScriptExecute command) {
    long start = System.currentTimeMillis();
    String executionId = command.getExecutionId();
    try {
      validate(command);
      rememberExecution(executionId);
      Map<String, byte[]> classes = ClassBundleCodec.decode(command.getClassBundle().toByteArray());
      if (!classes.containsKey(command.getEntryClass())) {
        throw new IllegalArgumentException("入口类不在 class bundle 中: " + command.getEntryClass());
      }
      Map<String, Object> arguments = parseArguments(command.getArgumentsJson());
      ScriptResult result =
          executeOnce(
              command.getEntryClass(),
              classes,
              new GmScriptContext(
                  executionId, ServerContext.getServerId(), command.getOperator(), arguments));
      return response(
          0,
          "",
          executionId,
          result.isSuccess(),
          result.getMessage(),
          MAPPER.writeValueAsString(result.getData()),
          start);
    } catch (Throwable error) {
      return response(
          1,
          errorMessage(error),
          executionId,
          false,
          "",
          "",
          start);
    }
  }

  private ScriptResult executeOnce(
      String entryClassName, Map<String, byte[]> classes, GmScriptContext context) throws Exception {
    RuntimeScriptClassLoader loader =
        new RuntimeScriptClassLoader(RuntimeScriptExecutor.class.getClassLoader(), classes);
    ClassLoader oldContextLoader = Thread.currentThread().getContextClassLoader();
    try {
      Thread.currentThread().setContextClassLoader(loader);
      Class<?> entryClass = loader.loadClass(entryClassName);
      if (!GmRuntimeScript.class.isAssignableFrom(entryClass)) {
        throw new IllegalArgumentException("入口类没有实现 ly.script.GmRuntimeScript");
      }
      GmRuntimeScript script =
          (GmRuntimeScript) entryClass.getDeclaredConstructor().newInstance();
      ScriptResult result = script.execute(context);
      return result == null ? ScriptResult.success("") : result;
    } finally {
      Thread.currentThread().setContextClassLoader(oldContextLoader);
      for (Class<?> definedClass : loader.getDefinedClasses()) {
        GroovySystem.getMetaClassRegistry().removeMetaClass(definedClass);
      }
      loader.close();
    }
  }

  private void validate(GmRuntimeScriptProto.csGmRuntimeScriptExecute command) throws Exception {
    if (command.getExecutionId().isBlank() || command.getExecutionId().length() > 128) {
      throw new IllegalArgumentException("executionId 不能为空且不能超过128字符");
    }
    if (!ServerContext.getServerId().equals(command.getTargetServerId())) {
      throw new IllegalArgumentException("脚本目标服务器不匹配");
    }
    if (command.getExpireAtMillis() <= System.currentTimeMillis()) {
      throw new IllegalArgumentException("脚本执行任务已过期");
    }
    synchronized (executionIds) {
      if (executionIds.contains(command.getExecutionId())) {
        throw new IllegalStateException("executionId 已执行，拒绝重复执行");
      }
    }
    if (!command.getEntryClass().matches("scripts(?:\\.[A-Za-z_$][A-Za-z0-9_$]*)+")) {
      throw new IllegalArgumentException("入口类必须位于 scripts 包");
    }
    if (!RuntimeScriptSignature.verify(
        command.getSignature(),
        command.getExecutionId(),
        command.getTargetServerId(),
        command.getEntryClass(),
        command.getOperator(),
        command.getArgumentsJson(),
        command.getSha256(),
        command.getExpireAtMillis())) {
      throw new SecurityException("临时脚本签名校验失败");
    }
    byte[] bundle = command.getClassBundle().toByteArray();
    String actual = HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bundle));
    if (!actual.equalsIgnoreCase(command.getSha256())) {
      throw new IllegalArgumentException("class bundle SHA-256 校验失败");
    }
    if (command.getArgumentsJson().getBytes(StandardCharsets.UTF_8).length > MAX_ARGUMENT_BYTES) {
      throw new IllegalArgumentException("脚本参数超过大小限制");
    }
  }

  private Map<String, Object> parseArguments(String json) throws Exception {
    if (json == null || json.isBlank()) {
      return Map.of();
    }
    return MAPPER.readValue(json, new TypeReference<Map<String, Object>>() {});
  }

  private void rememberExecution(String executionId) {
    synchronized (executionIds) {
      if (!executionIds.add(executionId)) {
        throw new IllegalStateException("executionId 已执行，拒绝重复执行");
      }
      executionOrder.addLast(executionId);
      while (executionOrder.size() > MAX_EXECUTION_HISTORY) {
        executionIds.remove(executionOrder.removeFirst());
      }
    }
  }

  private GmRuntimeScriptProto.scGmRuntimeScriptExecute response(
      int code,
      String error,
      String executionId,
      boolean success,
      String message,
      String resultJson,
      long start) {
    return GmRuntimeScriptProto.scGmRuntimeScriptExecute.newBuilder()
        .setCode(code)
        .setError(error == null ? "" : error)
        .setExecutionId(executionId == null ? "" : executionId)
        .setServerId(ServerContext.getServerId() == null ? "" : ServerContext.getServerId())
        .setSuccess(success)
        .setMessage(message == null ? "" : message)
        .setResultJson(resultJson == null ? "" : resultJson)
        .setElapsedMillis(System.currentTimeMillis() - start)
        .build();
  }

  private String errorMessage(Throwable error) {
    String message = error.getMessage();
    return error.getClass().getName() + (message == null || message.isBlank() ? "" : ": " + message);
  }
}
