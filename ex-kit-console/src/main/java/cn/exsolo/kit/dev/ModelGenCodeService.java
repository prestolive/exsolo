package cn.exsolo.kit.dev;

import cn.exsolo.kit.dev.bo.GenCodeBO;
import cn.exsolo.kit.dev.bo.ModelMetaBO;
import cn.exsolo.kit.utils.ExAssert;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author prestolive
 * @date 2024/6/27
 **/
@Service
public class ModelGenCodeService {

    public List<GenCodeBO> gen(ModelMetaBO meta,String tag){
        ExAssert.isNull(meta,meta.getBizName());
        List<GenCodeBO> list =new ArrayList<>();
        if("web-single-page".equals(tag)){
            GenCodeBO genCode= new GenCodeBO();
            genCode.setTag(tag);
            genCode.setLang("vue");
            genCode.setFileName(firstCharUpper(meta.getBizName())+"Manage.tsx");
            genCode.setFilePath("./"+genCode.getFileName());
            genCode.setDesc("单表增删改查单页");
            genCode.setContent(genCode(meta,"web-single-page.tsx.vm"));
            list.add(genCode);
        }
        return list;
    }

    private String genCode(ModelMetaBO meta,String templateName){
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.INPUT_ENCODING,"UTF-8");
        ve.setProperty(RuntimeConstants.OUTPUT_ENCODING,"UTF-8");
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        Template t = ve.getTemplate("template/"+templateName);
        VelocityContext ctx = new VelocityContext();
        ctx.put("packageName",meta.getPackageName());
        ctx.put("objectName",getObjectName(meta.getClzName()));
        ctx.put("bizName",firstCharLower(meta.getBizName()));
        ctx.put("BizName",firstCharUpper(meta.getBizName()));
        ctx.put("fields",meta.getFields());
        StringWriter sw = new StringWriter();
        t.merge(ctx, sw);
        System.out.println(sw.toString());
        return sw.toString();
    }

    private String getObjectName(String clzName){
        return clzName.substring(clzName.lastIndexOf(".")+1);
    }
    private String firstCharUpper(String str){
        return str.substring(0,1).toUpperCase(Locale.ROOT)+str.substring(1);
    }
    private String firstCharLower(String str){
        return str.substring(0,1).toLowerCase(Locale.ROOT)+str.substring(1);
    }

}
