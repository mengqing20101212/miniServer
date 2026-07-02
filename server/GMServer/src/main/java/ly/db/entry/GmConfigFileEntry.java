package ly.db.entry;

import java.time.LocalDateTime;
import ly.db.AbstractEntry;
import ly.db.DbMeta;

/** GM 上传的单个配表文件内容。 */
@DbMeta.DbTable(name = "gm_config_file")
public class GmConfigFileEntry extends AbstractEntry {
  private static final String[] DIRTY_FIELDS = {
    "id", "version", "file_name", "content", "content_md5", "file_size", "create_time", "update_time"
  };

  @DbMeta.DbMasterKey(name = "id", autoIncrement = true)
  @DbMeta.DbField(name = "id")
  private Long id;

  @DbMeta.DbField(name = "version")
  private String version;

  @DbMeta.DbField(name = "file_name")
  private String file_name;

  @DbMeta.DbField(name = "content", columnType = "LONGTEXT")
  private String content;

  @DbMeta.DbField(name = "content_md5")
  private String content_md5;

  @DbMeta.DbField(name = "file_size")
  private Integer file_size;

  @DbMeta.DbField(name = "create_time")
  private LocalDateTime create_time;

  @DbMeta.DbField(name = "update_time")
  private LocalDateTime update_time;

  public GmConfigFileEntry() {
    initDirtyState(DIRTY_FIELDS.length);
  }

  @Override
  protected String[] allDirtyFieldNames() {
    return DIRTY_FIELDS;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
    autoAddCurVersion();
    markFieldDirty(0);
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
    autoAddCurVersion();
    markFieldDirty(1);
  }

  public String getFileName() {
    return file_name;
  }

  public void setFileName(String fileName) {
    this.file_name = fileName;
    autoAddCurVersion();
    markFieldDirty(2);
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
    autoAddCurVersion();
    markFieldDirty(3);
  }

  public String getContentMd5() {
    return content_md5;
  }

  public void setContentMd5(String contentMd5) {
    this.content_md5 = contentMd5;
    autoAddCurVersion();
    markFieldDirty(4);
  }

  public Integer getFileSize() {
    return file_size;
  }

  public void setFileSize(Integer fileSize) {
    this.file_size = fileSize;
    autoAddCurVersion();
    markFieldDirty(5);
  }

  public LocalDateTime getCreateTime() {
    return create_time;
  }

  public void setCreateTime(LocalDateTime createTime) {
    this.create_time = createTime;
    autoAddCurVersion();
    markFieldDirty(6);
  }

  public LocalDateTime getUpdateTime() {
    return update_time;
  }

  public void setUpdateTime(LocalDateTime updateTime) {
    this.update_time = updateTime;
    autoAddCurVersion();
    markFieldDirty(7);
  }
}
