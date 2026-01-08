package com.clashruleslite.langchain4j.mcp.model;

import java.util.Map;

/**
 * MCP 工具调用请求。
 * <p>
 * MCP 中工具调用通常包含：工具名 + 参数（JSON 对象）。
 */
public record McpToolCall(String toolName, Map<String, Object> arguments) {
}
