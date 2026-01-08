package com.clashruleslite.langchain4j.mcp.rag;

import java.util.List;

/**
 * 重排序接口：可接入 cross-encoder 或 LLM 打分。
 */
public interface Reranker {
    List<Document> rerank(String query, List<Document> documents);
}
