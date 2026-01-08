package com.clashruleslite.langchain4j.mcp.examples;

import com.clashruleslite.langchain4j.mcp.McpClient;
import com.clashruleslite.langchain4j.mcp.model.McpToolCall;
import com.clashruleslite.langchain4j.mcp.model.McpToolResult;
import com.clashruleslite.langchain4j.mcp.transport.StdioMcpTransport;

import java.util.List;
import java.util.Map;

/**
 * MCP STDIO 示例：通过本地进程调用 MCP 工具。
 */
public final class McpStdioExample {
    public static void main(String[] args) {
        // 假设本地 MCP Server 提供可执行命令
        List<String> command = List.of("mcp-server", "--stdio");

        try (StdioMcpTransport transport = new StdioMcpTransport(command)) {
            McpClient client = new McpClient(transport);
            McpToolCall toolCall = new McpToolCall(
                    "calculator",
                    Map.of("expression", "(12 + 8) * 3")
            );

            McpToolResult result = client.callTool(toolCall);
            System.out.println("STDIO 工具返回: " + result.content());
        } catch (Exception e) {
            // 真实项目中建议进行进程健康检查与重启
            e.printStackTrace();
        }
    }
}
