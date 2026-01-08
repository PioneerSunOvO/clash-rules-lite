package com.clashruleslite.langchain4j.mcp.rag;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Advanced RAG：支持查询改写、多源检索与重排序。
 */
public final class AdvancedRagService {
    private final List<Retriever> retrievers;
    private final QueryTransformer queryTransformer;
    private final Reranker reranker;
    private final ChatModel chatModel;

    public AdvancedRagService(
            List<Retriever> retrievers,
            QueryTransformer queryTransformer,
            Reranker reranker,
            ChatModel chatModel
    ) {
        this.retrievers = retrievers;
        this.queryTransformer = queryTransformer;
        this.reranker = reranker;
        this.chatModel = chatModel;
    }

    /**
     * 高阶检索增强流程：
     * 1. 查询改写得到多个 query
     * 2. 多检索器并行检索并去重
     * 3. 重排序筛选最相关内容
     * 4. 生成回答
     */
    public String answer(String query, int topK) {
        List<String> expandedQueries = queryTransformer.transform(query);

        Set<Document> merged = new LinkedHashSet<>();
        for (String expanded : expandedQueries) {
            for (Retriever retriever : retrievers) {
                merged.addAll(retriever.retrieve(expanded, topK));
            }
        }

        List<Document> reranked = reranker.rerank(query, new ArrayList<>(merged));
        List<Document> selected = reranked.stream().limit(topK).toList();

        String context = selected.stream()
                .map(Document::content)
                .collect(Collectors.joining("\n---\n"));

        String prompt = "你是一个专家助手，请结合上下文回答问题。\n" +
                "上下文:\n" + context + "\n" +
                "问题: " + query + "\n" +
                "回答:";

        return chatModel.generate(prompt);
    }
}
