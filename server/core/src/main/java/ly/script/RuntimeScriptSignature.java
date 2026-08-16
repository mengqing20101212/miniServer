package ly.script;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import ly.ServerContext;

/** HMAC authentication for emergency class bundles sent over the internal RPC channel. */
public final class RuntimeScriptSignature {
  public static final String SECRET_PROPERTY = "miniserver.runtimeScriptSecret";
  public static final String SECRET_ENV = "MINISERVER_RUNTIME_SCRIPT_SECRET";
  private static final int MIN_SECRET_BYTES = 32;

  private RuntimeScriptSignature() {}

  public static String requireConfiguredSecret() {
    String secret = System.getProperty(SECRET_PROPERTY);
    if (secret == null || secret.isBlank()) {
      secret = System.getenv(SECRET_ENV);
    }
    if ((secret == null || secret.isBlank()) && ServerContext.serverConfig != null) {
      secret = ServerContext.serverConfig.getRuntimeScriptSecret();
    }
    if (secret == null || secret.getBytes(StandardCharsets.UTF_8).length < MIN_SECRET_BYTES) {
      throw new IllegalStateException(
          "临时脚本共享密钥未配置或少于 "
              + MIN_SECRET_BYTES
              + " 字节，请配置 Nacos runtimeScriptSecret、-D"
              + SECRET_PROPERTY
              + " 或环境变量 "
              + SECRET_ENV);
    }
    return secret;
  }

  public static String sign(
      String secret,
      String executionId,
      String targetServerId,
      String entryClass,
      String operator,
      String argumentsJson,
      String sha256,
      long expireAtMillis)
      throws Exception {
    Mac mac = Mac.getInstance("HmacSHA256");
    mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
    return HexFormat.of()
        .formatHex(
            mac.doFinal(
                canonicalBytes(
                    executionId,
                    targetServerId,
                    entryClass,
                    operator,
                    argumentsJson,
                    sha256,
                    expireAtMillis)));
  }

  public static boolean verify(
      String signature,
      String executionId,
      String targetServerId,
      String entryClass,
      String operator,
      String argumentsJson,
      String sha256,
      long expireAtMillis)
      throws Exception {
    String expected =
        sign(
            requireConfiguredSecret(),
            executionId,
            targetServerId,
            entryClass,
            operator,
            argumentsJson,
            sha256,
            expireAtMillis);
    return MessageDigest.isEqual(
        expected.getBytes(StandardCharsets.US_ASCII),
        (signature == null ? "" : signature.toLowerCase())
            .getBytes(StandardCharsets.US_ASCII));
  }

  private static byte[] canonicalBytes(
      String executionId,
      String targetServerId,
      String entryClass,
      String operator,
      String argumentsJson,
      String sha256,
      long expireAtMillis)
      throws Exception {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    try (DataOutputStream output = new DataOutputStream(bytes)) {
      writeString(output, executionId);
      writeString(output, targetServerId);
      writeString(output, entryClass);
      writeString(output, operator);
      writeString(output, argumentsJson);
      writeString(output, sha256);
      output.writeLong(expireAtMillis);
    }
    return bytes.toByteArray();
  }

  private static void writeString(DataOutputStream output, String value) throws Exception {
    byte[] bytes = (value == null ? "" : value).getBytes(StandardCharsets.UTF_8);
    output.writeInt(bytes.length);
    output.write(bytes);
  }
}
