package com.clashruleslite.langchain4j.mcp.rag;

import java.util.Map;

/**
 * Easy RAG 示例：最少配置即可运行的检索增强流程。
 */
public final class EasyRagExample {
    public static void main(String[] args) {
        EmbeddingModel embeddingModel = new DemoModels.DummyEmbeddingModel();
        VectorStore vectorStore = new InMemoryVectorStore();
        ChatModel chatModel = new DemoModels.DummyChatModel();

        // 模拟数据入库
        vectorStore.add(new Document("doc-1", "LangChain4J 支持与 MCP 交互。", Map.of()),
                embeddingModel.embed("LangChain4J 支持与 MCP 交互。"));
        vectorStore.add(new Document("doc-2", "SSE 适合基于 HTTP 的事件流通信。", Map.of()),
                embeddingModel.embed("SSE 适合基于 HTTP 的事件流通信。"));

        Retriever retriever = new Retriever(embeddingModel, vectorStore);
        EasyRagService ragService = new EasyRagService(retriever, chatModel);

        String answer = ragService.answer("什么是 SSE？", 2);
        System.out.println(answer);
    }

}
