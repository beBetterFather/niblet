package com.encdata.corn.niblet.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName ErrorController
 * @Description http错误码跳转地址
 * @Author SiweiJin
 * @Date 2019/8/22 10:22
 * @Version 1.0
 **/
@Controller
public class ErrorController {

    @RequestMapping("/404")
    public String notFound(){
        return "error/404";
    }

    @RequestMapping("/500")
    public String error(){
        return "error/500";
    }
}
