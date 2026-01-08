package com.clashruleslite.langchain4j.mcp.rag;

/**
 * 示例模型集合：便于本地演示。
 */
public final class DemoModels {
    private DemoModels() {
    }

    /**
     * Dummy 向量模型：仅示例使用，真实场景请替换为 LangChain4J EmbeddingModel。
     */
    public static final class DummyEmbeddingModel implements EmbeddingModel {
        @Override
        public float[] embed(String text) {
            // 简化：基于字符长度生成伪向量
            return new float[]{text.length(), text.length() / 2.0f};
        }
    }

    /**
     * Dummy Chat 模型：返回固定格式内容。
     */
    public static final class DummyChatModel implements ChatModel {
        @Override
        public String generate(String prompt) {
            return "[DummyAnswer] " + prompt;
        }
    }
}
