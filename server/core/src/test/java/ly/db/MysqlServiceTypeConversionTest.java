package ly.db;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class MysqlServiceTypeConversionTest {
  @Test
  public void packetEntryShouldConvertCommonMysqlTypes() {
    Map<String, Object> row = new HashMap<>();
    row.put("tiny_value", (byte) 7);
    row.put("small_unsigned", 65535);
    row.put("big_unsigned", new BigInteger("18446744073709551615"));
    row.put("amount", new BigDecimal("12.34"));
    row.put("flag", 1);
    row.put("name", "demo");
    row.put("json_text", "{\"a\":1}");
    row.put("date_value", Date.valueOf(LocalDate.of(2026, 4, 23)));
    row.put("time_value", Time.valueOf(LocalTime.of(12, 34, 56)));
    row.put("time_stamp", Timestamp.valueOf(LocalDateTime.of(2026, 4, 23, 12, 34, 56)));
    row.put("binary_value", new byte[] {1, 2, 3});

    MysqlTypeEntry entry = MysqlService.packetEntry(row, MysqlTypeEntry.class);

    assertEquals(Byte.valueOf((byte) 7), entry.getTinyValue());
    assertEquals(Integer.valueOf(65535), entry.getSmallUnsigned());
    assertEquals(new BigInteger("18446744073709551615"), entry.getBigUnsigned());
    assertEquals(new BigDecimal("12.34"), entry.getAmount());
    assertTrue(entry.getFlag());
    assertEquals("demo", entry.getName());
    assertEquals("{\"a\":1}", entry.getJsonText());
    assertEquals(LocalDate.of(2026, 4, 23), entry.getDateValue());
    assertEquals(LocalTime.of(12, 34, 56), entry.getTimeValue());
    assertEquals(LocalDateTime.of(2026, 4, 23, 12, 34, 56), entry.getTimeStamp());
    assertArrayEquals(new byte[] {1, 2, 3}, entry.getBinaryValue());
  }

  @DbMeta.DbTable(name = "mysql_type_demo")
  public static class MysqlTypeEntry extends AbstractEntry {
    @DbMeta.DbField(name = "tiny_value")
    private Byte tinyValue;

    @DbMeta.DbField(name = "small_unsigned")
    private Integer smallUnsigned;

    @DbMeta.DbField(name = "big_unsigned")
    private BigInteger bigUnsigned;

    @DbMeta.DbField(name = "amount")
    private BigDecimal amount;

    @DbMeta.DbField(name = "flag")
    private Boolean flag;

    @DbMeta.DbField(name = "name")
    private String name;

    @DbMeta.DbField(name = "json_text")
    private String jsonText;

    @DbMeta.DbField(name = "date_value")
    private LocalDate dateValue;

    @DbMeta.DbField(name = "time_value")
    private LocalTime timeValue;

    @DbMeta.DbField(name = "time_stamp")
    private LocalDateTime timeStamp;

    @DbMeta.DbField(name = "binary_value")
    private byte[] binaryValue;

    @Override
    protected String[] allDirtyFieldNames() {
      return new String[0];
    }

    public Byte getTinyValue() {
      return tinyValue;
    }

    public Integer getSmallUnsigned() {
      return smallUnsigned;
    }

    public BigInteger getBigUnsigned() {
      return bigUnsigned;
    }

    public BigDecimal getAmount() {
      return amount;
    }

    public Boolean getFlag() {
      return flag;
    }

    public String getName() {
      return name;
    }

    public String getJsonText() {
      return jsonText;
    }

    public LocalDate getDateValue() {
      return dateValue;
    }

    public LocalTime getTimeValue() {
      return timeValue;
    }

    public LocalDateTime getTimeStamp() {
      return timeStamp;
    }

    public byte[] getBinaryValue() {
      return binaryValue;
    }
  }
}
