package com.encdata.corn.niblet.dao;

import com.encdata.corn.niblet.domain.BlogRemark;
import com.encdata.corn.niblet.domain.BlogRemarkExample;

import java.util.List;

/**
 * BlogRemarkDao继承基类
 */
public interface BlogRemarkDao extends MyBatisBaseDao<BlogRemark, BlogRemark, BlogRemarkExample> {

    List<BlogRemark> queryBlogRemarks();

}