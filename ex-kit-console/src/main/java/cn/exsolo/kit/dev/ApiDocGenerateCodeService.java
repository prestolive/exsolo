package cn.exsolo.kit.dev;

import cn.exsolo.kit.console.ExKitConsoleErrorCodeEnum;
import cn.exsolo.kit.dev.bo.ApiDocBO;
import cn.exsolo.kit.dev.bo.ApiDocClzBO;
import cn.exsolo.kit.dev.bo.ApiDocTypeBO;
import cn.exsolo.kit.ex.ExErrorCodeException;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author prestolive
 * @date 2023/3/29
 **/
@Service
public class ApiDocGenerateCodeService {

    @Autowired
    private ApiDocService apiDocService;


    public String generateModule(String module){
        List<ApiDocClzBO> all = apiDocService.getAllController();
        List<String> classNames = all.stream().filter(row->row.getModule().equals(module)).map(row->row.getClz()).collect(Collectors.toList());
        return generate(classNames);
    }

    public String generateClass(String className){
        List<String> classNames = new ArrayList<>();
        classNames.add(className);
        return generate(classNames);
    }

    private String generate(List<String> classNames ){
        List<Class> classes = new ArrayList<>();
        for(String className:classNames){
            Class clz = null;
            try {
                clz = Class.forName(className);
                RequestMapping clzAnna = (RequestMapping) clz.getAnnotation(RequestMapping.class);
                if(clzAnna==null){
                    throw new ExErrorCodeException(ExKitConsoleErrorCodeEnum.NOT_REQUEST_MAPPING_ANNA,className);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e.getMessage(),e);
            }
            classes.add(clz);
        }
        //object param list
        List<ApiDocTypeBO> objectTypeList = new ArrayList<>();
        List<ApiDocBO> docList = new ArrayList<>();
        Set<String> exists = new HashSet<>();
        for(Class clz:classes){
            List<ApiDocBO> clzDocList = apiDocService.processClz(clz);
            docList.addAll(clzDocList);
            for(ApiDocBO doc:clzDocList){
                //pick all object
                if(doc.getReturnType()!=null){
                    pickObject(objectTypeList,exists,doc.getReturnType());
                }
                if(doc.getParamTypes()!=null){
                    doc.getParamTypes().forEach(row->pickObject(objectTypeList,exists,row));
                }
            }
        }
        //
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.INPUT_ENCODING,"UTF-8");
        ve.setProperty(RuntimeConstants.OUTPUT_ENCODING,"UTF-8");
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        Template t = ve.getTemplate("template/API.ts.vm");
        VelocityContext ctx = new VelocityContext();
        ctx.put("objectTypeList",objectTypeList);
        ctx.put("docList",docList);
        StringWriter sw = new StringWriter();
        t.merge(ctx, sw);
        System.out.println(sw.toString());
        return sw.toString();
    }

    /**
     * 提取对象类型
     * @param objectTypeList
     * @param typeBO
     */
    private void pickObject(List<ApiDocTypeBO> objectTypeList,Set<String> exists,ApiDocTypeBO typeBO){
        if(typeBO.getObject()&&!exists.contains(typeBO.getTsType())){
            objectTypeList.add(typeBO);
            exists.add(typeBO.getTsType());
        }
        if(typeBO.getFieldTypes()!=null){
            typeBO.getFieldTypes().forEach(row->pickObject(objectTypeList,exists,row));
        }
    }
}
