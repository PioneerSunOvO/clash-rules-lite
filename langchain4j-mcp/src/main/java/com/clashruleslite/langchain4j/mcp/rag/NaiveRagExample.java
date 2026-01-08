package com.clashruleslite.langchain4j.mcp.rag;

/**
 * Naive RAG 示例：包含分块与向量检索流程。
 */
public final class NaiveRagExample {
    public static void main(String[] args) {
        EmbeddingModel embeddingModel = new DemoModels.DummyEmbeddingModel();
        VectorStore vectorStore = new InMemoryVectorStore();
        ChatModel chatModel = new DemoModels.DummyChatModel();
        TextChunker chunker = new TextChunker(20, 5);

        NaiveRagService ragService = new NaiveRagService(embeddingModel, vectorStore, chatModel, chunker);
        ragService.ingest("guide", "LangChain4J 可以通过 MCP 调用工具，也可以直接接入 RAG 流程。");
        ragService.ingest("guide", "Naive RAG 使用向量检索，并将检索结果拼接到上下文中。");

        String answer = ragService.answer("Naive RAG 做了什么？", 3);
        System.out.println(answer);
    }
}
