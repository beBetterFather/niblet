package com.encdata.corn.niblet.web;

import com.encdata.core.JsonUtils;
import com.encdata.corn.niblet.dto.blog.Blog;
import com.encdata.corn.niblet.service.intf.BlogProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName BlogController
 * @Description 博客控制器
 * @Author SiweiJin
 * @Date 2019/8/22 11:37
 * @Version 1.0
 **/
@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired(required = false)
    private BlogProxy blogProxy;

    @Autowired
    private DataSource dataSource;

    @RequestMapping("/list")
    public ModelAndView blogList(ModelAndView model){
        List<Blog> blogs = blogProxy.getAllBlogs();
        model.addObject("blogs", blogs);
        model.setViewName("blog/list");
        return model;
    }

    @RequestMapping("db")
    @ResponseBody
    public String db() throws SQLException {
        return JsonUtils.toJson(dataSource.toString()) ;
    }

}
