package ly.script;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Test;

public class ClassBundleCodecTest {
  private static final byte[] MINIMAL_CLASS_HEADER = {
    (byte) 0xca, (byte) 0xfe, (byte) 0xba, (byte) 0xbe
  };

  @Test
  public void roundTripsGeneratedClasses() {
    Map<String, byte[]> classes = new LinkedHashMap<>();
    classes.put("scripts.EmergencyFix", MINIMAL_CLASS_HEADER);
    classes.put("scripts.EmergencyFix$Helper", MINIMAL_CLASS_HEADER);

    Map<String, byte[]> decoded = ClassBundleCodec.decode(ClassBundleCodec.encode(classes));

    assertEquals(classes.keySet(), decoded.keySet());
    assertArrayEquals(MINIMAL_CLASS_HEADER, decoded.get("scripts.EmergencyFix"));
  }

  @Test
  public void rejectsClassesOutsideScriptsPackage() {
    assertThrows(
        IllegalArgumentException.class,
        () -> ClassBundleCodec.encode(Map.of("ly.EmergencyFix", MINIMAL_CLASS_HEADER)));
  }

  @Test
  public void rejectsTrailingBundleData() {
    byte[] encoded =
        ClassBundleCodec.encode(Map.of("scripts.EmergencyFix", MINIMAL_CLASS_HEADER));
    byte[] corrupted = Arrays.copyOf(encoded, encoded.length + 1);

    assertThrows(IllegalArgumentException.class, () -> ClassBundleCodec.decode(corrupted));
  }
}
