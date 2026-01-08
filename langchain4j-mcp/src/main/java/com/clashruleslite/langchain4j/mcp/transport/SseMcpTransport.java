package com.clashruleslite.langchain4j.mcp.transport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * 基于 SSE 的 MCP 传输层。
 * <p>
 * - send(): 通过 HTTP POST 发送 JSON 请求
 * - read(): 通过 SSE 流读取响应
 */
public final class SseMcpTransport implements McpTransport {
    private final HttpClient httpClient;
    private final URI rpcEndpoint;
    private final BufferedReader sseReader;
    private final String bearerToken;

    public SseMcpTransport(URI rpcEndpoint, URI sseEndpoint, String bearerToken) {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.rpcEndpoint = rpcEndpoint;
        this.bearerToken = bearerToken;
        this.sseReader = openSseStream(sseEndpoint);
    }

    @Override
    public void send(String jsonPayload) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(rpcEndpoint)
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .header("Content-Type", "application/json");
        if (bearerToken != null && !bearerToken.isBlank()) {
            builder.header("Authorization", "Bearer " + bearerToken);
        }
        try {
            httpClient.send(builder.build(), HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("SSE MCP 请求发送失败", e);
        }
    }

    @Override
    public String read() {
        try {
            String line;
            while ((line = sseReader.readLine()) != null) {
                // SSE 数据行以 "data:" 开头
                if (line.startsWith("data:")) {
                    return line.substring("data:".length()).trim();
                }
            }
            return null;
        } catch (IOException e) {
            throw new IllegalStateException("读取 SSE 数据失败", e);
        }
    }

    @Override
    public void close() throws IOException {
        sseReader.close();
    }

    private BufferedReader openSseStream(URI sseEndpoint) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(sseEndpoint)
                .GET()
                .header("Accept", "text/event-stream");
        if (bearerToken != null && !bearerToken.isBlank()) {
            builder.header("Authorization", "Bearer " + bearerToken);
        }
        try {
            HttpResponse<java.io.InputStream> response = httpClient.send(
                    builder.build(),
                    HttpResponse.BodyHandlers.ofInputStream()
            );
            return new BufferedReader(new InputStreamReader(response.body(), StandardCharsets.UTF_8));
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("SSE 连接失败", e);
        }
    }
}
