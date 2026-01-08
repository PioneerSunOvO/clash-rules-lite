package com.clashruleslite.langchain4j.mcp.rag;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Easy RAG：最简单的检索增强生成。
 */
public final class EasyRagService {
    private final Retriever retriever;
    private final ChatModel chatModel;

    public EasyRagService(Retriever retriever, ChatModel chatModel) {
        this.retriever = retriever;
        this.chatModel = chatModel;
    }

    /**
     * 直接检索 topK 文档并拼接上下文生成回答。
     */
    public String answer(String query, int topK) {
        List<Document> documents = retriever.retrieve(query, topK);
        String context = documents.stream()
                .map(Document::content)
                .collect(Collectors.joining("\n---\n"));

        String prompt = "你是一个专业助理，请根据上下文回答问题。\n" +
                "上下文:\n" + context + "\n" +
                "问题: " + query + "\n" +
                "回答:";

        return chatModel.generate(prompt);
    }
}
