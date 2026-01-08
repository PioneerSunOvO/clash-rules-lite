package com.clashruleslite.langchain4j.mcp.rag;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * 基于关键词覆盖的简易重排序器。
 */
public final class KeywordReranker implements Reranker {
    @Override
    public List<Document> rerank(String query, List<Document> documents) {
        String[] keywords = query.toLowerCase(Locale.ROOT).split("\\s+");
        return documents.stream()
                .sorted(Comparator.comparingInt(document -> -score(document.content(), keywords)))
                .toList();
    }

    private int score(String content, String[] keywords) {
        String lower = content.toLowerCase(Locale.ROOT);
        int score = 0;
        for (String keyword : keywords) {
            if (!keyword.isBlank() && lower.contains(keyword)) {
                score++;
            }
        }
        return score;
    }
}
