package com.encdata.corn.niblet.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: jinsi
 * @Date: 2019/7/16 14:09
 * @Description: 图片
 */
@Controller
public class PictureController {

    @RequestMapping("/images")
    public ModelAndView images(ModelAndView mv){
        mv.setViewName("carousel");
        return mv;
    }
}
