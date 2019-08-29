package com.encdata.corn.niblet.dao;

import com.encdata.corn.niblet.domain.BlogContent;
import com.encdata.corn.niblet.domain.BlogContentExample;

import java.util.List;

/**
 * BlogContentDao继承基类
 */
public interface BlogContentDao extends MyBatisBaseDao<BlogContent, Long, BlogContentExample> {

    List<BlogContent> queryBlogContent();
}