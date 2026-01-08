package com.clashruleslite.langchain4j.mcp.transport;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 基于 STDIO 的 MCP 传输层。
 * <p>
 * - send(): 写入标准输入（按行发送 JSON）
 * - read(): 从标准输出读取一行 JSON
 */
public final class StdioMcpTransport implements McpTransport {
    private final Process process;
    private final BufferedWriter stdinWriter;
    private final BufferedReader stdoutReader;

    public StdioMcpTransport(List<String> command) {
        this.process = startProcess(command);
        this.stdinWriter = new BufferedWriter(new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8));
        this.stdoutReader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
    }

    @Override
    public void send(String jsonPayload) {
        try {
            stdinWriter.write(jsonPayload);
            stdinWriter.newLine();
            stdinWriter.flush();
        } catch (IOException e) {
            throw new IllegalStateException("STDIO 写入失败", e);
        }
    }

    @Override
    public String read() {
        try {
            return stdoutReader.readLine();
        } catch (IOException e) {
            throw new IllegalStateException("STDIO 读取失败", e);
        }
    }

    @Override
    public void close() throws IOException {
        stdinWriter.close();
        stdoutReader.close();
        process.destroy();
    }

    private Process startProcess(List<String> command) {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectError(ProcessBuilder.Redirect.INHERIT);
        try {
            return builder.start();
        } catch (IOException e) {
            throw new IllegalStateException("启动 MCP STDIO 进程失败", e);
        }
    }
}
