package com.clashruleslite.langchain4j.mcp.rag;

/**
 * 向量模型接口（可替换为 LangChain4J 的 EmbeddingModel）。
 */
public interface EmbeddingModel {
    /**
     * 将文本转为向量。
     */
    float[] embed(String text);
}
