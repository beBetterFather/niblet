package com.encdata.corn.niblet.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: jinsi
 * @Date: 2019/7/16 09:39
 * @Description:
 */
@Controller
@RequestMapping("/config")
public class AutoController {

    @RequestMapping("/auth")
    public ModelAndView auth(ModelAndView mv){
        mv.setViewName("table");
        return mv;
    }
}
