package com.encdata.corn.niblet.utils;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @ClassName TemplateParseUtils
 * @Description 將模板转换成内容工具类
 * @Author SiweiJin
 * @Date 2019/8/21 17:10
 * @Version 1.0
 **/
public class TemplateParseUtils {

    private static final TemplateEngine templateEngine = ApplicationContextRegister.getBean(TemplateEngine.class);

   /**
    * @Description //TODO
    * @Date 17:19 2019/8/21
    * @Param template context
    * @return string
    * @Author SiweiJin
    **/
    public static String parse(String template, Context context){
        return templateEngine.process(template, context);
    }
}
