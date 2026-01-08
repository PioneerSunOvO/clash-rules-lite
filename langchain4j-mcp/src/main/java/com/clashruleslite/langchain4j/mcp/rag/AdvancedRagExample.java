package com.clashruleslite.langchain4j.mcp.rag;

import java.util.List;
import java.util.Map;

/**
 * Advanced RAG 示例：查询改写 + 多源检索 + 重排序。
 */
public final class AdvancedRagExample {
    public static void main(String[] args) {
        EmbeddingModel embeddingModel = new DemoModels.DummyEmbeddingModel();
        VectorStore vectorStoreA = new InMemoryVectorStore();
        VectorStore vectorStoreB = new InMemoryVectorStore();
        ChatModel chatModel = new DemoModels.DummyChatModel();

        // 模拟多源数据
        vectorStoreA.add(new Document("kb-1", "MCP 支持通过 STDIO 连接本地工具。", Map.of("source", "kbA")),
                embeddingModel.embed("MCP 支持通过 STDIO 连接本地工具。"));
        vectorStoreB.add(new Document("kb-2", "Advanced RAG 通常包含重排序步骤。", Map.of("source", "kbB")),
                embeddingModel.embed("Advanced RAG 通常包含重排序步骤。"));

        Retriever retrieverA = new Retriever(embeddingModel, vectorStoreA);
        Retriever retrieverB = new Retriever(embeddingModel, vectorStoreB);

        QueryTransformer transformer = query -> List.of(
                query,
                query + " LangChain4J",
                query + " MCP"
        );

        Reranker reranker = new KeywordReranker();

        AdvancedRagService ragService = new AdvancedRagService(
                List.of(retrieverA, retrieverB),
                transformer,
                reranker,
                chatModel
        );

        String answer = ragService.answer("如何进行 Advanced RAG？", 3);
        System.out.println(answer);
    }
}
