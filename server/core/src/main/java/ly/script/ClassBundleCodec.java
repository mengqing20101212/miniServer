package ly.script;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/** Compact binary representation of all class files generated for one Groovy source. */
public final class ClassBundleCodec {
  private static final int MAGIC = 0x4D534352;
  private static final int VERSION = 1;
  public static final int MAX_BUNDLE_BYTES = 1024 * 1024;
  public static final int MAX_CLASS_BYTES = 256 * 1024;
  public static final int MAX_CLASS_COUNT = 100;

  private ClassBundleCodec() {}

  public static byte[] encode(Map<String, byte[]> classes) {
    if (classes == null || classes.isEmpty() || classes.size() > MAX_CLASS_COUNT) {
      throw new IllegalArgumentException("class 数量必须在 1-" + MAX_CLASS_COUNT + " 之间");
    }
    try {
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      try (DataOutputStream output = new DataOutputStream(bytes)) {
        output.writeInt(MAGIC);
        output.writeInt(VERSION);
        output.writeInt(classes.size());
        for (Map.Entry<String, byte[]> entry : classes.entrySet()) {
          validateClass(entry.getKey(), entry.getValue());
          output.writeUTF(entry.getKey());
          output.writeInt(entry.getValue().length);
          output.write(entry.getValue());
        }
      }
      byte[] bundle = bytes.toByteArray();
      if (bundle.length > MAX_BUNDLE_BYTES) {
        throw new IllegalArgumentException("class bundle 超过 " + MAX_BUNDLE_BYTES + " 字节");
      }
      return bundle;
    } catch (IOException e) {
      throw new IllegalStateException("编码 class bundle 失败", e);
    }
  }

  public static Map<String, byte[]> decode(byte[] bundle) {
    if (bundle == null || bundle.length == 0 || bundle.length > MAX_BUNDLE_BYTES) {
      throw new IllegalArgumentException("class bundle 为空或超过大小限制");
    }
    try (DataInputStream input = new DataInputStream(new ByteArrayInputStream(bundle))) {
      if (input.readInt() != MAGIC || input.readInt() != VERSION) {
        throw new IllegalArgumentException("class bundle 格式或版本错误");
      }
      int count = input.readInt();
      if (count <= 0 || count > MAX_CLASS_COUNT) {
        throw new IllegalArgumentException("class bundle 数量错误: " + count);
      }
      Map<String, byte[]> classes = new LinkedHashMap<>();
      for (int i = 0; i < count; i++) {
        String className = input.readUTF();
        int length = input.readInt();
        if (length <= 0 || length > MAX_CLASS_BYTES) {
          throw new IllegalArgumentException("class 大小错误: " + className);
        }
        byte[] classBytes = input.readNBytes(length);
        if (classBytes.length != length) {
          throw new IllegalArgumentException("class bundle 数据不完整: " + className);
        }
        validateClass(className, classBytes);
        if (classes.put(className, classBytes) != null) {
          throw new IllegalArgumentException("class bundle 包含重复类: " + className);
        }
      }
      if (input.available() != 0) {
        throw new IllegalArgumentException("class bundle 包含尾随数据");
      }
      return classes;
    } catch (IOException e) {
      throw new IllegalArgumentException("解析 class bundle 失败", e);
    }
  }

  private static void validateClass(String className, byte[] bytes) {
    if (className == null
        || !className.matches("scripts(?:\\.[A-Za-z_$][A-Za-z0-9_$]*)+")) {
      throw new IllegalArgumentException("脚本类必须位于 scripts 包: " + className);
    }
    if (bytes == null || bytes.length < 4 || bytes.length > MAX_CLASS_BYTES) {
      throw new IllegalArgumentException("class 字节大小错误: " + className);
    }
    if ((bytes[0] & 0xff) != 0xca
        || (bytes[1] & 0xff) != 0xfe
        || (bytes[2] & 0xff) != 0xba
        || (bytes[3] & 0xff) != 0xbe) {
      throw new IllegalArgumentException("非法 class 文件: " + className);
    }
  }
}
