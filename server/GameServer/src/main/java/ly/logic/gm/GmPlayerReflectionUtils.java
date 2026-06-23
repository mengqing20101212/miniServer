package ly.logic.gm;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** GM 玩家详情用的对象展开和字段 patch 工具。 */
final class GmPlayerReflectionUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private GmPlayerReflectionUtils() {
    }

    static String toJson(Object value) {
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(toPlainObject(value, new IdentityHashMap<>()));
        } catch (Exception e) {
            throw new IllegalStateException("object to json failed", e);
        }
    }

    static void patch(Object root, String path, String newValueJson) {
        if (root == null) {
            throw new IllegalArgumentException("root is null");
        }
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("path is blank");
        }
        String[] parts = path.split("\\.");
        Object current = root;
        for (int i = 0; i < parts.length - 1; i++) {
            current = readPart(current, parts[i]);
        }
        writePart(current, parts[parts.length - 1], newValueJson);
    }

    private static Object toPlainObject(Object value, IdentityHashMap<Object, Boolean> visited) {
        if (value == null || value instanceof String || value instanceof Number || value instanceof Boolean
                || value instanceof Enum<?>) {
            return value;
        }
        if (visited.containsKey(value)) {
            return "[cycle]";
        }
        visited.put(value, Boolean.TRUE);
        if (value instanceof Map<?, ?> map) {
            Map<String, Object> result = new LinkedHashMap<>();
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                result.put(String.valueOf(entry.getKey()), toPlainObject(entry.getValue(), visited));
            }
            return result;
        }
        if (value instanceof Iterable<?> iterable) {
            List<Object> result = new ArrayList<>();
            for (Object item : iterable) {
                result.add(toPlainObject(item, visited));
            }
            return result;
        }
        if (value.getClass().isArray()) {
            int length = java.lang.reflect.Array.getLength(value);
            List<Object> result = new ArrayList<>(length);
            for (int i = 0; i < length; i++) {
                result.add(toPlainObject(java.lang.reflect.Array.get(value, i), visited));
            }
            return result;
        }
        Map<String, Object> result = new LinkedHashMap<>();
        for (Field field : allFields(value.getClass())) {
            if (shouldSkipField(field)) {
                continue;
            }
            try {
                field.setAccessible(true);
                result.put(field.getName(), toPlainObject(field.get(value), visited));
            } catch (Exception e) {
                result.put(field.getName(), "[read error:" + e.getMessage() + "]");
            }
        }
        return result;
    }

    private static Object readPart(Object current, String part) {
        if (current == null) {
            throw new IllegalArgumentException("path parent is null, part=" + part);
        }
        IndexedPart indexed = IndexedPart.parse(part);
        Object value;
        if (current instanceof Map<?, ?> map) {
            value = readMap(map, indexed.name());
        } else {
            Field field = findEditableField(current.getClass(), indexed.name(), false);
            value = getFieldValue(current, field);
        }
        if (indexed.index() != null) {
            return readIndex(value, indexed.index());
        }
        return value;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void writePart(Object current, String part, String newValueJson) {
        if (current == null) {
            throw new IllegalArgumentException("path parent is null, part=" + part);
        }
        IndexedPart indexed = IndexedPart.parse(part);
        Object parsed = parseJsonValue(newValueJson);
        if (current instanceof Map map) {
            Object oldKey = findMapKey(map, indexed.name());
            if (indexed.index() != null) {
                Object list = map.get(oldKey);
                writeIndex(list, indexed.index(), parsed, Object.class);
                return;
            }
            Class<?> valueType = oldKey == null || map.get(oldKey) == null ? Object.class : map.get(oldKey).getClass();
            map.put(oldKey == null ? indexed.name() : oldKey, MAPPER.convertValue(parsed, valueType));
            return;
        }
        Field field = findEditableField(current.getClass(), indexed.name(), true);
        if (indexed.index() != null) {
            writeIndex(getFieldValue(current, field), indexed.index(), parsed, Object.class);
            return;
        }
        setFieldValue(current, field, MAPPER.convertValue(parsed, field.getType()));
    }

    private static Object parseJsonValue(String value) {
        try {
            if (value == null || value.isBlank()) {
                return null;
            }
            return MAPPER.readValue(value, Object.class);
        } catch (Exception e) {
            return value;
        }
    }

    private static Object readMap(Map<?, ?> map, String keyText) {
        Object key = findMapKey(map, keyText);
        if (key == null && !map.containsKey(null)) {
            throw new IllegalArgumentException("map key not found: " + keyText);
        }
        return map.get(key);
    }

    private static Object findMapKey(Map<?, ?> map, String keyText) {
        for (Object key : map.keySet()) {
            if (String.valueOf(key).equals(keyText)) {
                return key;
            }
        }
        return null;
    }

    private static Object readIndex(Object value, int index) {
        if (value instanceof List<?> list) {
            return list.get(index);
        }
        if (value != null && value.getClass().isArray()) {
            return java.lang.reflect.Array.get(value, index);
        }
        throw new IllegalArgumentException("target is not list or array");
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void writeIndex(Object value, int index, Object newValue, Class<?> fallbackType) {
        if (value instanceof List list) {
            Object old = list.get(index);
            Class<?> targetType = old == null ? fallbackType : old.getClass();
            list.set(index, MAPPER.convertValue(newValue, targetType));
            return;
        }
        if (value != null && value.getClass().isArray()) {
            Object old = java.lang.reflect.Array.get(value, index);
            Class<?> targetType = old == null ? value.getClass().getComponentType() : old.getClass();
            java.lang.reflect.Array.set(value, index, MAPPER.convertValue(newValue, targetType));
            return;
        }
        throw new IllegalArgumentException("target is not list or array");
    }

    private static Object getFieldValue(Object target, Field field) {
        try {
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception e) {
            throw new IllegalArgumentException("read field failed: " + field.getName(), e);
        }
    }

    private static void setFieldValue(Object target, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new IllegalArgumentException("write field failed: " + field.getName(), e);
        }
    }

    private static Field findEditableField(Class<?> type, String fieldName, boolean requireWritable) {
        for (Field field : allFields(type)) {
            if (!field.getName().equals(fieldName)) {
                continue;
            }
            if (shouldSkipField(field) || (requireWritable && Modifier.isFinal(field.getModifiers()))) {
                throw new IllegalArgumentException("field is not editable: " + fieldName);
            }
            return field;
        }
        throw new IllegalArgumentException("field not found: " + fieldName + ", class=" + type.getName());
    }

    private static List<Field> allFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        Class<?> current = type;
        while (current != null && current != Object.class) {
            fields.addAll(List.of(current.getDeclaredFields()));
            current = current.getSuperclass();
        }
        return fields;
    }

    private static boolean shouldSkipField(Field field) {
        int modifiers = field.getModifiers();
        return Modifier.isStatic(modifiers)
                || Modifier.isTransient(modifiers)
                || "player".equals(field.getName())
                || "class".equals(field.getName());
    }

    private record IndexedPart(String name, Integer index) {
        static IndexedPart parse(String raw) {
            int bracket = raw.indexOf('[');
            if (bracket < 0) {
                return new IndexedPart(raw, null);
            }
            int end = raw.indexOf(']', bracket);
            if (end < 0) {
                throw new IllegalArgumentException("invalid list path part: " + raw);
            }
            return new IndexedPart(raw.substring(0, bracket), Integer.parseInt(raw.substring(bracket + 1, end)));
        }
    }
}
