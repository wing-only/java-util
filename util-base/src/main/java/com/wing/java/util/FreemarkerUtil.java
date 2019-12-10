package com.wing.java.util;

import com.wing.java.util.exception.ExceptionConstant;
import com.wing.java.util.param.Result;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class FreemarkerUtil {

    private static Logger logger = LoggerFactory.getLogger(FreemarkerUtil.class);

    /**
     * 解析String字符串模板
     * @param templateStr
     * @param model
     * @return
     */
    public static String processString(String templateStr, Map<String,Object> model){
        if(CommonUtil.isEmpty(templateStr)
                || !templateStr.contains("${")){
            return templateStr;
        }

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setTagSyntax(Configuration.AUTO_DETECT_TAG_SYNTAX);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        StringTemplateLoader stringLoader = new StringTemplateLoader();
        String templateName = "stringTemplate";
        stringLoader.putTemplate(templateName,templateStr);
        cfg.setTemplateLoader(stringLoader);

        String result = templateStr;
        try {
            Template template = cfg.getTemplate(templateName);
            //模板解析后输出
            StringWriter stringWriter = new StringWriter();
            template.process(model, stringWriter);
            result = stringWriter.toString();
        } catch (Exception e) {
            logger.error("freemarker processString error, errorMessage : " + e.getMessage());
            return null;
        }

        return result;
    }


    /**
     * 根据模板文件生成对应的文件
     * @param templatePath 模板文件路径
     * @param model 参数值
     * @param filePath 生成的文件路径
     */
//    public static void processTemplate(String templatePath, String templateName, Map<String,Object> model,String filePath,String fileName)
//            throws IOException, TemplateException {
//
//        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
//        cfg.setTagSyntax(Configuration.AUTO_DETECT_TAG_SYNTAX);
//        cfg.setDefaultEncoding("UTF-8");
//        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
//
//        cfg.setDirectoryForTemplateLoading(new File(templatePath));
//        Template template = cfg.getTemplate(templateName);
//        StringWriter stringWriter = new StringWriter();
//        template.process(model, stringWriter);
//        Result r = FileUtil.createFile(fileName,filePath,stringWriter.toString().replace("\r\n","\n"));
//        if(r.getCode() != ExceptionConstant.SUCCESS){
//            throw new RuntimeException(r.getMsg());
//        }
//    }

}
