package ly.db;

import org.apache.fury.Fury;
import org.apache.fury.ThreadSafeFury;
import org.apache.fury.config.CompatibleMode;
import org.apache.fury.config.Language;

/** Thread-safe compatible snapshots; only deserialize application-owned local data. */
public final class FurySerializer {
  private static final ThreadSafeFury FURY = Fury.builder()
      .withLanguage(Language.JAVA).requireClassRegistration(false)
      .withCompatibleMode(CompatibleMode.COMPATIBLE).withRefTracking(true)
      .withAsyncCompilation(true).buildThreadSafeFury();
  private FurySerializer() {}
  public static byte[] serialize(Object value) { return FURY.serialize(value); }
  public static Object deserialize(byte[] bytes) { return FURY.deserialize(bytes); }
}
