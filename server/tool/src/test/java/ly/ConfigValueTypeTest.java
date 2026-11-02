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
    public void readsNestedListsAndMapsWithConcreteScalarTypes() {
        ConfigValueType type = ConfigValueType.parse(" MAP < STR, List < LONG > > ");
        assertEquals("map<string,list<long>>", type.declaration());
        assertEquals(Map.of("cost", List.of(10L, 20L)), type.readCell("{\"cost\":[10,20]}"));
        assertEquals(List.of(Map.of(1, true), Map.of(2, false)),
                ConfigValueType.parse("list<map<int,bool>>").readCell("[{\"1\":true},{\"2\":0}]"));
        assertEquals(Map.of(1, "one", 2, "two"),
                ConfigValueType.parse("map<int,string>").readCell("[[1,\"one\"],[2,\"two\"]]"));
    }

    @Test
    public void reportsUnsupportedTypesAndInvalidCellValuesAtTheInput() {
        for (String type : List.of("struct<id:int>", "unknown", "map<list<int>,int>", "list<>", "int extra"))
            assertThrows(type, IllegalArgumentException.class, () -> ConfigValueType.parse(type));
        for (String value : List.of("1.5", "abc", "2147483648"))
            assertThrows(value, IllegalArgumentException.class, () -> ConfigValueType.parse("int").readCell(value));
        assertThrows(IllegalArgumentException.class, () -> ConfigValueType.parse("bool").readCell("yes"));
        assertThrows(IllegalArgumentException.class, () -> ConfigValueType.parse("list<int>").readCell("[1,\"bad\"]"));
        ConfigLoadException error = assertThrows(ConfigLoadException.class,
                () -> ConfigValues.read(ConfigValueType.parse("list<int>"), "[1,\"bad\"]", "demo:行6:字段cost"));
        assertTrue(error.getMessage().contains("demo:行6:字段cost"));
        assertTrue(error.getMessage().contains("$[1]"));
    }
}
