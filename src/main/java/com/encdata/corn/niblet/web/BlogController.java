package com.encdata.corn.niblet.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping("/list")
    public ModelAndView blogList(ModelAndView model){
        model.setViewName("blog/list");
        return model;
    }
}
