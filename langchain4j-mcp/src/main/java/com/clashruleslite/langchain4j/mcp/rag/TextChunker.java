package com.clashruleslite.langchain4j.mcp.rag;

import java.util.ArrayList;
import java.util.List;

/**
 * 简易分块器：按固定长度切分文本。
 */
public final class TextChunker {
    private final int chunkSize;
    private final int overlap;

    public TextChunker(int chunkSize, int overlap) {
        if (chunkSize <= 0) {
            throw new IllegalArgumentException("chunkSize 必须大于 0");
        }
        if (overlap < 0 || overlap >= chunkSize) {
            throw new IllegalArgumentException("overlap 必须在 [0, chunkSize) 范围内");
        }
        this.chunkSize = chunkSize;
        this.overlap = overlap;
    }

    /**
     * 将文本切分为多个 chunk。
     */
    public List<String> split(String text) {
        List<String> chunks = new ArrayList<>();
        int start = 0;
        while (start < text.length()) {
            int end = Math.min(start + chunkSize, text.length());
            chunks.add(text.substring(start, end));
            start = end - overlap;
            if (start < 0) {
                start = 0;
            }
        }
        return chunks;
    }
}
