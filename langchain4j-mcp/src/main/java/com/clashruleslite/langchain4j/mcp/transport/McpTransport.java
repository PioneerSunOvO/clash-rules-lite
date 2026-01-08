package com.clashruleslite.langchain4j.mcp.transport;

import java.io.Closeable;

/**
 * MCP 传输层抽象。
 * <p>
 * 通过统一接口支持 SSE、STDIO 等多种通信方式。
 */
public interface McpTransport extends Closeable {

    /**
     * 发送 MCP 请求 JSON 字符串。
     */
    void send(String jsonPayload);

    /**
     * 读取下一条 MCP 响应（阻塞读取）。
     */
    String read();
}
