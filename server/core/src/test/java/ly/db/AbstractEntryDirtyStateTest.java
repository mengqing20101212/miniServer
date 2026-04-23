package ly.db;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import ly.db.entry.PlayerEntry;
import ly.utils.BitSwitchState;
import org.junit.Test;

public class AbstractEntryDirtyStateTest {
  @Test
  public void playerEntryShouldTrackDirtyFields() {
    PlayerEntry entry = new PlayerEntry();

    assertFalse(canSave(entry));
    assertArrayEquals(new String[0], entry.getDirtyFieldNames());

    entry.setName("test");
    entry.setLevel(10);

    assertTrue(canSave(entry));
    assertArrayEquals(new String[] {"name", "level"}, entry.getDirtyFieldNames());

    entry.markPersisted();

    assertFalse(canSave(entry));
    assertArrayEquals(new String[0], entry.getDirtyFieldNames());
  }

  @Test
  public void bitSwitchStateShouldPickCompactStorage() {
    assertTrue(BitSwitchState.of(8).getRawValue() instanceof Byte);
    assertTrue(BitSwitchState.of(16).getRawValue() instanceof Short);
    assertTrue(BitSwitchState.of(32).getRawValue() instanceof Integer);
    assertTrue(BitSwitchState.of(64).getRawValue() instanceof Long);
    assertTrue(BitSwitchState.of(65).getRawValue() instanceof long[]);
  }

  private boolean canSave(AbstractEntry entry) {
    try {
      Method method = AbstractEntry.class.getDeclaredMethod("canSave");
      method.setAccessible(true);
      return (boolean) method.invoke(entry);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
