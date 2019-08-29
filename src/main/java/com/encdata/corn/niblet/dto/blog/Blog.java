package com.encdata.corn.niblet.dto.blog;

import com.encdata.corn.niblet.domain.BlogTag;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @ClassName Blog
 * @Description 页面展示blog对象
 * @Author SiweiJin
 * @Date 2019/8/23 13:35
 * @Version 1.0
 **/
@Setter
@Getter
@ToString
public class Blog {

    private long id;

    private String title;

    private String author;

    @DateTimeFormat(style = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String content;

    private List<BlogTag> tags;

    //评论数
    private int remarkCount;

    //浏览数
    private int viewCount;

}
