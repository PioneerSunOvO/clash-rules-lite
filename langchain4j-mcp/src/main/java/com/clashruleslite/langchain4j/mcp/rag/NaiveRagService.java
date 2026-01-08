package com.clashruleslite.langchain4j.mcp.rag;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Naive RAG：引入分块与向量检索的基础版本。
 */
public final class NaiveRagService {
    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;
    private final ChatModel chatModel;
    private final TextChunker chunker;

    public NaiveRagService(
            EmbeddingModel embeddingModel,
            VectorStore vectorStore,
            ChatModel chatModel,
            TextChunker chunker
    ) {
        this.embeddingModel = embeddingModel;
        this.vectorStore = vectorStore;
        this.chatModel = chatModel;
        this.chunker = chunker;
    }

    /**
     * 文档入库：分块 + 向量化 + 写入向量库。
     */
    public void ingest(String sourceId, String text) {
        List<String> chunks = chunker.split(text);
        for (int i = 0; i < chunks.size(); i++) {
            String chunk = chunks.get(i);
            String id = sourceId + "-" + i + "-" + UUID.randomUUID();
            Document document = new Document(id, chunk, Map.of("source", sourceId));
            vectorStore.add(document, embeddingModel.embed(chunk));
        }
    }

    /**
     * 检索 + 生成回答。
     */
    public String answer(String query, int topK) {
        float[] queryEmbedding = embeddingModel.embed(query);
        List<Document> documents = vectorStore.search(queryEmbedding, topK);
        String context = documents.stream()
                .map(Document::content)
                .collect(Collectors.joining("\n---\n"));

        String prompt = "请基于以下上下文回答问题。\n" +
                "上下文:\n" + context + "\n" +
                "问题: " + query + "\n" +
                "回答:";
        return chatModel.generate(prompt);
    }
}
