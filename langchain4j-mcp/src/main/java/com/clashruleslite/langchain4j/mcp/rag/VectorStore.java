package com.clashruleslite.langchain4j.mcp.rag;

import java.util.List;

/**
 * 向量存储抽象，可替换为 Milvus / Qdrant / PgVector 等。
 */
public interface VectorStore {

    /**
     * 保存文本与向量。
     */
    void add(Document document, float[] embedding);

    /**
     * 基于向量进行 TopK 检索。
     */
    List<Document> search(float[] queryEmbedding, int topK);
}
