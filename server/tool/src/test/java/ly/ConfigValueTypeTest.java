package ly;

import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class ConfigValueTypeTest {
    @Test
    public void readsPrimitiveTypesAndEmptyCells() {
        Object[][] examples = {
                {"byte", "12", (byte) 12}, {"short", "32000", (short) 32000},
                {"int", "123456", 123456}, {"long", "9223372036854775807", Long.MAX_VALUE},
                {"float", "1.25", 1.25F}, {"double", "2.75", 2.75D},
                {"boolean", "1", true}, {"bool", "false", false},
                {"string", "1001;2|1002;3", "1001;2|1002;3"},
                {"int", "", 0}, {"bool", "", false}, {"string", "", ""},
                {"list<int>", "", List.of()}, {"map<int,long>", "", Map.of()}
        };
        for (Object[] example : examples) {
            assertEquals(example[0].toString(), example[2],
                    ConfigValueType.parse(example[0].toString()).readCell(example[1].toString()));
        }
    }

    @Test
    public void readsSemicolonListsAndColonMapsWithConcreteScalarTypes() {
        ConfigValueType type = ConfigValueType.parse(" MAP < INT, LONG > ");
        assertEquals("map<int,long>", type.declaration());
        assertEquals(Map.of(1001, 200L, 1002, 300L), type.readCell("1001:200;1002:300"));
        assertEquals(List.of(1, 2, 3), ConfigValueType.parse("list<int>").readCell("1; 2;3"));
        assertEquals(List.of("普通", "稀有"), ConfigValueType.parse("list<string>").readCell("普通;稀有"));
        assertEquals(List.of(1.5D, 2.25D), ConfigValueType.parse("list<double>").readCell("1.5;2.25"));
        assertEquals(Map.of(1, true, 2, false), ConfigValueType.parse("map<int,bool>").readCell("1:true;2:0"));
        assertEquals(Map.of("提示", "键:值"), ConfigValueType.parse("map<string,string>").readCell("提示:键:值"));
    }

    @Test
    public void reportsUnsupportedTypesAndInvalidCellValuesAtTheInput() {
        for (String type : List.of("struct<id:int>", "unknown", "map<list<int>,int>", "map<string,list<double>>", "list<map<int,bool>>", "list<list<int>>", "list<>", "int extra"))
            assertThrows(type, IllegalArgumentException.class, () -> ConfigValueType.parse(type));
        for (String value : List.of("1.5", "abc", "2147483648"))
            assertThrows(value, IllegalArgumentException.class, () -> ConfigValueType.parse("int").readCell(value));
        assertThrows(IllegalArgumentException.class, () -> ConfigValueType.parse("bool").readCell("yes"));
        assertThrows(IllegalArgumentException.class, () -> ConfigValueType.parse("list<int>").readCell("1;bad"));
        ConfigLoadException error = assertThrows(ConfigLoadException.class,
                () -> ConfigValues.read(ConfigValueType.parse("list<int>"), "1;bad", "demo:行6:字段cost"));
        assertTrue(error.getMessage().contains("demo:行6:字段cost"));
        assertTrue(error.getMessage().contains("$[1]"));
        assertThrows(IllegalArgumentException.class, () -> ConfigValueType.parse("map<int,long>").readCell("1:2;bad"));
    }
}
