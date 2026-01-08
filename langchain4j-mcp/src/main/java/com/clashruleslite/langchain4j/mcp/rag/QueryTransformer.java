package com.clashruleslite.langchain4j.mcp.rag;

import java.util.List;

/**
 * 查询改写/扩展接口。
 */
public interface QueryTransformer {
    List<String> transform(String query);
}
