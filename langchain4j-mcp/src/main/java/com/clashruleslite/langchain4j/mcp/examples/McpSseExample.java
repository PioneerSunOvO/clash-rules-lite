package com.clashruleslite.langchain4j.mcp.examples;

import com.clashruleslite.langchain4j.mcp.McpClient;
import com.clashruleslite.langchain4j.mcp.model.McpToolCall;
import com.clashruleslite.langchain4j.mcp.model.McpToolResult;
import com.clashruleslite.langchain4j.mcp.transport.SseMcpTransport;

import java.net.URI;
import java.util.Map;

/**
 * MCP SSE 示例：通过 HTTP + SSE 调用工具。
 */
public final class McpSseExample {
    public static void main(String[] args) {
        URI rpcEndpoint = URI.create("https://mcp.example.com/rpc");
        URI sseEndpoint = URI.create("https://mcp.example.com/sse");

        try (SseMcpTransport transport = new SseMcpTransport(rpcEndpoint, sseEndpoint, "YOUR_TOKEN")) {
            McpClient client = new McpClient(transport);
            McpToolCall toolCall = new McpToolCall(
                    "web-search",
                    Map.of("query", "LangChain4J MCP SSE 示例")
            );

            McpToolResult result = client.callTool(toolCall);
            System.out.println("SSE 工具返回: " + result.content());
        } catch (Exception e) {
            // 真实项目中可接入日志框架并进行重试处理
            e.printStackTrace();
        }
    }
}
