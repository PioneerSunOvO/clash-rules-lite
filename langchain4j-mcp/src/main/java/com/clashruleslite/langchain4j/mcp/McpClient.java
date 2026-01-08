package com.clashruleslite.langchain4j.mcp;

import com.clashruleslite.langchain4j.mcp.model.McpToolCall;
import com.clashruleslite.langchain4j.mcp.model.McpToolResult;
import com.clashruleslite.langchain4j.mcp.transport.McpTransport;
import com.clashruleslite.langchain4j.mcp.util.JsonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * MCP 客户端封装。
 * <p>
 * 负责将 MCP 工具调用请求封装为 JSON，并读取返回结果。
 */
public final class McpClient {
    private final McpTransport transport;

    public McpClient(McpTransport transport) {
        this.transport = transport;
    }

    /**
     * 调用 MCP 工具并返回文本结果。
     */
    public McpToolResult callTool(McpToolCall toolCall) {
        String requestId = UUID.randomUUID().toString();
        Map<String, Object> payload = new HashMap<>();
        payload.put("jsonrpc", "2.0");
        payload.put("id", requestId);
        payload.put("method", "tools/call");

        Map<String, Object> params = new HashMap<>();
        params.put("name", toolCall.toolName());
        params.put("arguments", toolCall.arguments());
        payload.put("params", params);

        transport.send(JsonUtils.toJson(payload));

        // 读取 MCP 返回，这里简化为单行 JSON 文本
        String responseJson = transport.read();
        return new McpToolResult(responseJson);
    }
}
