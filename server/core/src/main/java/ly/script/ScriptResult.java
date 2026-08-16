package ly.script;

import java.util.LinkedHashMap;
import java.util.Map;

/** Script-defined result returned to GM after a one-shot execution. */
public final class ScriptResult {
  private final boolean success;
  private final String message;
  private final Map<String, Object> data;

  public ScriptResult(boolean success, String message, Map<String, Object> data) {
    this.success = success;
    this.message = message == null ? "" : message;
    this.data = data == null ? Map.of() : new LinkedHashMap<>(data);
  }

  public static ScriptResult success(String message) {
    return new ScriptResult(true, message, Map.of());
  }

  public static ScriptResult success(Map<String, Object> data) {
    return new ScriptResult(true, "", data);
  }

  public static ScriptResult success(String message, Map<String, Object> data) {
    return new ScriptResult(true, message, data);
  }

  public static ScriptResult failure(String message) {
    return new ScriptResult(false, message, Map.of());
  }

  public boolean isSuccess() {
    return success;
  }

  public String getMessage() {
    return message;
  }

  public Map<String, Object> getData() {
    return data;
  }
}
