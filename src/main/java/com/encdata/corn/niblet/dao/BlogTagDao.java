package com.encdata.corn.niblet.dao;

import com.encdata.corn.niblet.domain.BlogTag;
import com.encdata.corn.niblet.domain.BlogTagExample;

import java.util.List;

/**
 * BlogTagDao继承基类
 */
public interface BlogTagDao extends MyBatisBaseDao<BlogTag, BlogTag, BlogTagExample> {

    List<BlogTag> queryBlogTags();

}