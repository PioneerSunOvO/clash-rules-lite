package com.clashruleslite.langchain4j.mcp.rag;

/**
 * 简化版聊天模型接口（可替换为 LangChain4J ChatLanguageModel）。
 */
public interface ChatModel {
    /**
     * 传入 prompt，返回模型回答。
     */
    String generate(String prompt);
}
