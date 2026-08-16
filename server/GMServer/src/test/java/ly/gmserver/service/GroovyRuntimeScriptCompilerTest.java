package ly.gmserver.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.protobuf.ByteString;
import java.util.UUID;
import ly.ServerContext;
import ly.gmserver.service.GroovyRuntimeScriptCompiler.CompilationResult;
import ly.proto.GmRuntimeScriptProto;
import ly.script.RuntimeScriptExecutor;
import ly.script.RuntimeScriptSignature;
import org.junit.jupiter.api.Test;

class GroovyRuntimeScriptCompilerTest {
  private final GroovyRuntimeScriptCompiler compiler = new GroovyRuntimeScriptCompiler();

  @Test
  void compilesAndExecutesInlineBundleOnlyOnce() throws Exception {
    String source =
        """
        package scripts

        import ly.script.GmRuntimeScript
        import ly.script.GmScriptContext
        import ly.script.ScriptResult

        class EmergencyFix implements GmRuntimeScript {
            ScriptResult execute(GmScriptContext context) {
                return ScriptResult.success("done", [
                    serverId: context.serverId,
                    value: context.arguments.value
                ])
            }
        }
        """;
    CompilationResult compiled = compiler.compile(source, "scripts.EmergencyFix");
    String executionId = "test-" + UUID.randomUUID();
    String secret = "test-runtime-script-secret-32-bytes-minimum";
    long expireAtMillis = System.currentTimeMillis() + 60_000;
    ServerContext.SERVER_ID = "game-test";
    System.setProperty(RuntimeScriptSignature.SECRET_PROPERTY, secret);
    GmRuntimeScriptProto.csGmRuntimeScriptExecute request =
        GmRuntimeScriptProto.csGmRuntimeScriptExecute.newBuilder()
            .setExecutionId(executionId)
            .setTargetServerId("game-test")
            .setEntryClass("scripts.EmergencyFix")
            .setOperator("test")
            .setArgumentsJson("{\"value\":7}")
            .setClassBundle(ByteString.copyFrom(compiled.bundle()))
            .setSha256(compiled.sha256())
            .setExpireAtMillis(expireAtMillis)
            .setSignature(
                RuntimeScriptSignature.sign(
                    secret,
                    executionId,
                    "game-test",
                    "scripts.EmergencyFix",
                    "test",
                    "{\"value\":7}",
                    compiled.sha256(),
                    expireAtMillis))
            .build();

    try {
      GmRuntimeScriptProto.scGmRuntimeScriptExecute tampered =
          RuntimeScriptExecutor.getInstance()
              .execute(
                  request.toBuilder()
                      .setExecutionId("tampered-" + UUID.randomUUID())
                      .build());
      GmRuntimeScriptProto.scGmRuntimeScriptExecute first =
          RuntimeScriptExecutor.getInstance().execute(request);
      GmRuntimeScriptProto.scGmRuntimeScriptExecute replay =
          RuntimeScriptExecutor.getInstance().execute(request);

      assertFalse(tampered.getSuccess());
      assertTrue(tampered.getError().contains("签名"));
      assertTrue(first.getSuccess(), first.getError());
      assertEquals("done", first.getMessage());
      assertTrue(first.getResultJson().contains("\"serverId\":\"game-test\""));
      assertTrue(first.getResultJson().contains("\"value\":7"));
      assertFalse(replay.getSuccess());
      assertTrue(replay.getError().contains("executionId"));
    } finally {
      System.clearProperty(RuntimeScriptSignature.SECRET_PROPERTY);
    }
  }

  @Test
  void rejectsEntryThatDoesNotImplementContract() {
    String source = "package scripts\nclass InvalidFix {}";

    assertThrows(
        IllegalArgumentException.class,
        () -> compiler.compile(source, "scripts.InvalidFix"));
  }
}
