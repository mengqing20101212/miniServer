package ly.script;

/** Entry point implemented by every one-shot GM runtime script. */
public interface GmRuntimeScript {
  ScriptResult execute(GmScriptContext context) throws Exception;
}
