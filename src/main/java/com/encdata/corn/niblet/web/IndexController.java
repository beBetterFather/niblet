package com.encdata.corn.niblet.web;

import com.encdata.core.mail.MailHepler;
import com.encdata.corn.niblet.domain.User;
import com.encdata.corn.niblet.utils.JsonUtil;
import com.encdata.corn.niblet.utils.TemplateParseUtils;
import com.encdata.corn.niblet.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.Context;

/**
 * @Author: jinsi
 * @Date: 2019/7/16 13:26
 * @Description: 首页
 */
@Controller
public class IndexController {

    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/index")
    public ModelAndView index(ModelAndView mv) throws Exception {
        mv.setViewName("index");
        mv.addObject("admin", UserUtil.isAdmin());
        mv.addObject("ops", false);
        mv.addObject("user", UserUtil.getCurrentUser());
        mv.addObject("role", UserUtil.getUserRoles());

        //发送邮件
        String from = "464833126@qq.com";
        String to   = "243539095@qq.com";
        String subject = "主题：图个乐";
        Context context = new Context();
        context.setVariable("id", "006");
        MailHepler.sendMsgWithTemplate(from, to, subject, TemplateParseUtils.parse("verify", context));
        return mv;
    }
}
