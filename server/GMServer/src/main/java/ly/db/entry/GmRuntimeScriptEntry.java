package ly.db.entry;

import java.time.LocalDateTime;
import ly.db.AbstractEntry;
import ly.db.DbMeta;

/** Audit record for one GM compilation and one-shot server execution. */
@DbMeta.DbTable(name = "gm_runtime_script")
public class GmRuntimeScriptEntry extends AbstractEntry {
  private static final String[] DIRTY_FIELDS = {
    "id",
    "execution_id",
    "target_server_id",
    "entry_class",
    "source_code",
    "arguments_json",
    "class_sha256",
    "status",
    "compile_message",
    "result_json",
    "error_message",
    "operator",
    "elapsed_millis",
    "create_time",
    "update_time"
  };

  @DbMeta.DbMasterKey(name = "id", autoIncrement = true)
  @DbMeta.DbField(name = "id")
  private Long id;

  @DbMeta.DbField(name = "execution_id")
  private String execution_id;

  @DbMeta.DbField(name = "target_server_id")
  private String target_server_id;

  @DbMeta.DbField(name = "entry_class")
  private String entry_class;

  @DbMeta.DbField(name = "source_code", columnType = "LONGTEXT")
  private String source_code;

  @DbMeta.DbField(name = "arguments_json", columnType = "LONGTEXT")
  private String arguments_json;

  @DbMeta.DbField(name = "class_sha256")
  private String class_sha256;

  @DbMeta.DbField(name = "status")
  private String status;

  @DbMeta.DbField(name = "compile_message", columnType = "LONGTEXT")
  private String compile_message;

  @DbMeta.DbField(name = "result_json", columnType = "LONGTEXT")
  private String result_json;

  @DbMeta.DbField(name = "error_message", columnType = "LONGTEXT")
  private String error_message;

  @DbMeta.DbField(name = "operator")
  private String operator;

  @DbMeta.DbField(name = "elapsed_millis")
  private Long elapsed_millis;

  @DbMeta.DbField(name = "create_time")
  private LocalDateTime create_time;

  @DbMeta.DbField(name = "update_time")
  private LocalDateTime update_time;

  public GmRuntimeScriptEntry() {
    initDirtyState(DIRTY_FIELDS.length);
  }

  @Override
  protected String[] allDirtyFieldNames() {
    return DIRTY_FIELDS;
  }

  public Long getId() { return id; }
  public void setId(Long value) { id = value; mark(0); }
  public String getExecutionId() { return execution_id; }
  public void setExecutionId(String value) { execution_id = value; mark(1); }
  public String getTargetServerId() { return target_server_id; }
  public void setTargetServerId(String value) { target_server_id = value; mark(2); }
  public String getEntryClass() { return entry_class; }
  public void setEntryClass(String value) { entry_class = value; mark(3); }
  public String getSourceCode() { return source_code; }
  public void setSourceCode(String value) { source_code = value; mark(4); }
  public String getArgumentsJson() { return arguments_json; }
  public void setArgumentsJson(String value) { arguments_json = value; mark(5); }
  public String getClassSha256() { return class_sha256; }
  public void setClassSha256(String value) { class_sha256 = value; mark(6); }
  public String getStatus() { return status; }
  public void setStatus(String value) { status = value; mark(7); }
  public String getCompileMessage() { return compile_message; }
  public void setCompileMessage(String value) { compile_message = value; mark(8); }
  public String getResultJson() { return result_json; }
  public void setResultJson(String value) { result_json = value; mark(9); }
  public String getErrorMessage() { return error_message; }
  public void setErrorMessage(String value) { error_message = value; mark(10); }
  public String getOperator() { return operator; }
  public void setOperator(String value) { operator = value; mark(11); }
  public Long getElapsedMillis() { return elapsed_millis; }
  public void setElapsedMillis(Long value) { elapsed_millis = value; mark(12); }
  public LocalDateTime getCreateTime() { return create_time; }
  public void setCreateTime(LocalDateTime value) { create_time = value; mark(13); }
  public LocalDateTime getUpdateTime() { return update_time; }
  public void setUpdateTime(LocalDateTime value) { update_time = value; mark(14); }

  private void mark(int index) {
    autoAddCurVersion();
    markFieldDirty(index);
  }
}
