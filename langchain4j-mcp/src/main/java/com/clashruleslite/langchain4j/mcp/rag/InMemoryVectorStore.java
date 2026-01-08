package com.clashruleslite.langchain4j.mcp.rag;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 简易内存向量库，仅用于示例。
 */
public final class InMemoryVectorStore implements VectorStore {
    private final List<Entry> entries = new ArrayList<>();

    @Override
    public void add(Document document, float[] embedding) {
        entries.add(new Entry(document, embedding));
    }

    @Override
    public List<Document> search(float[] queryEmbedding, int topK) {
        return entries.stream()
                .sorted(Comparator.comparingDouble(entry -> -cosineSimilarity(queryEmbedding, entry.embedding)))
                .limit(topK)
                .map(entry -> entry.document)
                .toList();
    }

    private double cosineSimilarity(float[] left, float[] right) {
        double dot = 0.0;
        double leftNorm = 0.0;
        double rightNorm = 0.0;
        int length = Math.min(left.length, right.length);
        for (int i = 0; i < length; i++) {
            dot += left[i] * right[i];
            leftNorm += left[i] * left[i];
            rightNorm += right[i] * right[i];
        }
        if (leftNorm == 0 || rightNorm == 0) {
            return 0.0;
        }
        return dot / (Math.sqrt(leftNorm) * Math.sqrt(rightNorm));
    }

    private record Entry(Document document, float[] embedding) {
    }
}
