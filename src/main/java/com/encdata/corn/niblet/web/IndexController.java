package com.encdata.corn.niblet.web;

import com.encdata.corn.niblet.domain.User;
import com.encdata.corn.niblet.utils.JsonUtil;
import com.encdata.corn.niblet.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: jinsi
 * @Date: 2019/7/16 13:26
 * @Description: 首页
 */
@Controller
public class IndexController {

    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/index")
    public ModelAndView index(ModelAndView mv){
        mv.setViewName("index");
        mv.addObject("admin", UserUtil.isAdmin());
        mv.addObject("ops", false);
        mv.addObject("user", UserUtil.getCurrentUser());
        mv.addObject("role", UserUtil.getUserRoles());
        return mv;
    }
}
