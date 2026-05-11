package ly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;

/** 配置表检测基类，负责统一校验 #fields/#types 表头，并提供自定义数据检测扩展点。 */
public abstract class AbstractConfigChecker<T> {

  public abstract String getConfigFileName();

  public abstract List<ConfigColumnMeta> getExpectedColumns();

  public final void checkHeader(Logger logger, String configDir) throws ConfigLoadException {
    File file = new File(configDir + File.separator + getConfigFileName());
    if (!file.exists()) {
      throw new ConfigLoadException("Config file does not exist :" + file.getAbsolutePath());
    }
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String fieldsLine = br.readLine();
      String typesLine = br.readLine();
      checkLine(file, fieldsLine, "#fields", true);
      checkLine(file, typesLine, "#types", false);
    } catch (IOException e) {
      throw new ConfigLoadException("Config file could not be read :" + file.getAbsolutePath());
    }
  }

  public final void checkAfterParse(Logger logger, List<T> configs) throws ConfigLoadException {
    customCheck(logger, configs);
  }

  protected void customCheck(Logger logger, List<T> configs) throws ConfigLoadException {}

  private void checkLine(File file, String line, String prefix, boolean checkName)
      throws ConfigLoadException {
    if (line == null) {
      throw new ConfigLoadException(file.getName() + " missing " + prefix + " line");
    }
    String[] arr = line.split("\t", -1);
    if (arr.length == 0 || !prefix.equals(arr[0])) {
      throw new ConfigLoadException(file.getName() + " first column must be " + prefix);
    }
    List<ConfigColumnMeta> expected = getExpectedColumns();
    if (arr.length - 1 != expected.size()) {
      throw new ConfigLoadException(
          String.format(
              "%s %s column size mismatch, java=%d, file=%d",
              file.getName(), prefix, expected.size(), arr.length - 1));
    }
    for (ConfigColumnMeta column : expected) {
      String actual = arr[column.index() + 1].trim();
      String expect = checkName ? column.name() : normalizeType(column.type());
      if (!checkName) {
        actual = normalizeType(actual);
      }
      if (!expect.equals(actual)) {
        String kind = checkName ? "field" : "type";
        throw new ConfigLoadException(
            String.format(
                "%s %s mismatch at column %d, java=%s, file=%s",
                file.getName(), kind, column.index(), expect, actual));
      }
    }
  }

  private String normalizeType(String type) {
    return type == null ? "" : type.trim().toUpperCase();
  }
}
