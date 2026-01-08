# LangChain4J + MCP 模块

本模块提供一套**可直接落地**的 LangChain4J 调用 MCP（Model Context Protocol）示例与实战 RAG 模式：

- **MCP 连接方式**：SSE、STDIO（已补全常见调用路径）。
- **RAG 三种方式**：Easy RAG、Naive RAG、Advanced RAG（支持查询改写、多源检索、重排序）。

> 说明：示例代码不依赖第三方库，便于阅读与迁移；当接入真实 LangChain4J 时，只需替换 `EmbeddingModel`、`ChatModel` 与向量检索实现即可。

## 目录结构

```
langchain4j-mcp/
├─ README.md
└─ src/main/java/com/clashruleslite/langchain4j/mcp/
   ├─ examples/                # 入口示例
   ├─ model/                   # MCP 消息模型
   ├─ rag/                     # RAG 实战封装
   ├─ transport/               # SSE / STDIO 传输层
   └─ util/                    # JSON 工具
```

## MCP 调用方式示例

### 1) SSE（Server-Sent Events）

- 适合 HTTP(S) 场景
- 通过 `text/event-stream` 接收 MCP 事件流

示例入口：`examples/McpSseExample.java`

### 2) STDIO

- 适合本地 MCP Server（例如命令行工具）
- 通过标准输入/输出传递 MCP 消息

示例入口：`examples/McpStdioExample.java`

## RAG 三种方式

### Easy RAG

- 直接向量检索 + 拼接上下文
- 适合 PoC 或低成本快速上线

示例入口：`rag/EasyRagExample.java`

### Naive RAG

- 基于向量搜索的基础实现
- 添加简单的 chunk 策略与 TopK 过滤

示例入口：`rag/NaiveRagExample.java`

### Advanced RAG

- 查询改写（query transformation）
- 多源检索（multi-retriever）
- 重排序（re-ranking）
- 可扩展为 Graph RAG / Agentic RAG

示例入口：`rag/AdvancedRagExample.java`

## 使用建议

1. 先阅读 `examples` 目录，理解 MCP 连接方式。
2. 再阅读 `rag` 目录，从 Easy RAG 逐步升级到 Advanced RAG。
3. 接入真实项目时，用 LangChain4J 的模型与向量检索实现替换示例接口即可。
