package com.clashruleslite.langchain4j.mcp.rag;

import java.util.List;

/**
 * 基础检索器：封装 embedding + 向量检索逻辑。
 */
public final class Retriever {
    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;

    public Retriever(EmbeddingModel embeddingModel, VectorStore vectorStore) {
        this.embeddingModel = embeddingModel;
        this.vectorStore = vectorStore;
    }

    public List<Document> retrieve(String query, int topK) {
        float[] embedding = embeddingModel.embed(query);
        return vectorStore.search(embedding, topK);
    }
}
