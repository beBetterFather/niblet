package com.encdata.corn.niblet.dao;

import com.encdata.corn.niblet.domain.BlogBrowse;
import com.encdata.corn.niblet.domain.BlogBrowseExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * BlogBrowseDao继承基类
 */
public interface BlogBrowseDao extends MyBatisBaseDao<BlogBrowse, Long, BlogBrowseExample> {

    //根据id获取每篇文章liulansh
    List<BlogBrowse> getBlogBrowse(@Param("ids") List<Long> ids);
}