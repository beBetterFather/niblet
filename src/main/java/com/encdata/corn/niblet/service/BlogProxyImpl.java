package com.encdata.corn.niblet.service;

import com.encdata.corn.niblet.dao.BlogBrowseDao;
import com.encdata.corn.niblet.dao.BlogContentDao;
import com.encdata.corn.niblet.dao.BlogRemarkDao;
import com.encdata.corn.niblet.dao.BlogTagDao;
import com.encdata.corn.niblet.domain.*;
import com.encdata.corn.niblet.dto.blog.Blog;
import com.encdata.corn.niblet.service.intf.BlogProxy;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName BlogProxyImpl
 * @Description 博客处理
 * @Author SiweiJin
 * @Date 2019/8/23 13:46
 * @Version 1.0
 **/
@Service
public class BlogProxyImpl implements BlogProxy {

    @Autowired(required = false)
    private BlogContentDao blogContentDao;

    @Autowired(required = false)
    private BlogBrowseDao blogBrowseDao;

    @Autowired(required = false)
    private BlogRemarkDao blogRemarkDao;

    @Autowired(required = false)
    private BlogTagDao blogTagDao;

    @Override
    public List<Blog> getAllBlogs() {
        List<Blog> blogs = Lists.newArrayList();

        List<Long> ids = Lists.newArrayList();
        //获取所有的博客
        List<BlogContent> contents = blogContentDao.queryBlogContent();
        //获取所有id
        for(BlogContent content : contents){
            Blog blog = new Blog();
            blog.setId(content.getId());
            blog.setTitle(content.getTitle());
            blog.setAuthor(content.getAuthor());
            blog.setCreateTime(content.getCreateTime());
            blog.setContent(content.getContent());
            blogs.add(blog);
            ids.add(content.getId());
        }
        //获取浏览数
        List<BlogBrowse> views = blogBrowseDao.getBlogBrowse(ids);
        Map<Long, Integer> viewsMap = Maps.newHashMap();
        for (BlogBrowse browse: views){
            viewsMap.put(browse.getContentId(), browse.getCount());
        }
        for(Blog blog: blogs){
            blog.setViewCount(viewsMap.get(blog.getId()));
        }

        //获取评论
        List<BlogRemark> remarks = blogRemarkDao.queryBlogRemarks();
        Map<Long, Integer> remarkMap = Maps.newHashMap();
        for(BlogRemark remark : remarks){
            if(!remarkMap.containsKey(remark.getContentId())){
                remarkMap.put(remark.getContentId(), 1);
            }else{
                remarkMap.put(remark.getContentId(), remarkMap.get(remark.getContentId())+1);
            }
        }
        for(Blog blog: blogs){
            blog.setRemarkCount(remarkMap.get(blog.getId()));
        }

        //获取标签
        List<BlogTag> tags = blogTagDao.queryBlogTags();
        Map<Long, List<BlogTag>> tagsMap = Maps.newHashMap();
        for(BlogTag tag : tags){
            if(!tagsMap.containsKey(tag.getContentId())){
                List<BlogTag> newList = Lists.newArrayList();
                newList.add(tag);
                tagsMap.put(tag.getContentId(), newList);
            }else{
                List<BlogTag> oldList = tagsMap.get(tag.getContentId());
                oldList.add(tag);
                tagsMap.put(tag.getContentId(), oldList);
            }
        }
        for(Blog blog : blogs){
            blog.setTags(tagsMap.get(blog.getId()));
        }
        return blogs;
    }
}
