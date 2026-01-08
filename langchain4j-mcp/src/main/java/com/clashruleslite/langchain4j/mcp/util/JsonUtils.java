package com.clashruleslite.langchain4j.mcp.util;

import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 轻量 JSON 序列化工具，仅覆盖示例所需类型。
 * <p>
 * 真实项目中建议替换为 Jackson / Gson 等成熟库。
 */
public final class JsonUtils {
    private JsonUtils() {
    }

    /**
     * 将对象序列化为 JSON 字符串。
     * 支持 String / Number / Boolean / Map / Collection / null。
     */
    public static String toJson(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return "\"" + escape((String) value) + "\"";
        }
        if (value instanceof Number || value instanceof Boolean) {
            return value.toString();
        }
        if (value instanceof Map<?, ?> map) {
            return toJsonObject(map);
        }
        if (value instanceof Collection<?> collection) {
            return toJsonArray(collection);
        }
        // 兜底：将未知对象转为字符串
        return "\"" + escape(value.toString()) + "\"";
    }

    private static String toJsonObject(Map<?, ?> map) {
        StringJoiner joiner = new StringJoiner(",", "{", "}");
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            String key = String.valueOf(entry.getKey());
            joiner.add("\"" + escape(key) + "\":" + toJson(entry.getValue()));
        }
        return joiner.toString();
    }

    private static String toJsonArray(Collection<?> collection) {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        for (Object item : collection) {
            joiner.add(toJson(item));
        }
        return joiner.toString();
    }

    private static String escape(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
