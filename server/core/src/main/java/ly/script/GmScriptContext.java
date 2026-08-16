package ly.script;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** Minimal immutable execution metadata. Business objects are acquired by the script itself. */
public final class GmScriptContext {
  private final String executionId;
  private final String serverId;
  private final String operator;
  private final Map<String, Object> arguments;

  public GmScriptContext(
      String executionId, String serverId, String operator, Map<String, Object> arguments) {
    this.executionId = executionId;
    this.serverId = serverId;
    this.operator = operator;
    this.arguments =
        Collections.unmodifiableMap(
            arguments == null ? Map.of() : new LinkedHashMap<>(arguments));
  }

  public String getExecutionId() {
    return executionId;
  }

  public String getServerId() {
    return serverId;
  }

  public String getOperator() {
    return operator;
  }

  public Map<String, Object> getArguments() {
    return arguments;
  }
}
