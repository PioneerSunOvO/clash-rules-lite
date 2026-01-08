package com.clashruleslite.langchain4j.mcp.rag;

import java.util.Map;

/**
 * 文档载体：包含原文内容与可选元数据。
 */
public record Document(String id, String content, Map<String, String> metadata) {
}
