package cn.exsolo.kit.dev;

import cn.exsolo.comm.ex.ExDeclaredException;
import cn.exsolo.comm.utils.TsUtil;
import cn.exsolo.kit.console.ExKitConsoleCodeGenEnum;
import cn.exsolo.kit.console.ExKitConsoleErrorCodeEnum;
import cn.exsolo.kit.dev.bo.GenCodeBO;
import cn.exsolo.kit.dev.bo.ModelMetaBO;
import org.apache.commons.lang3.StringUtils;
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
 * @date 2021/6/27
 **/
@Service
public class ModelGenCodeService {

    public List<GenCodeBO> gen(ModelMetaBO meta, String tag) {
        if (StringUtils.isEmpty(meta.getSysName())) {
            throw new ExDeclaredException(ExKitConsoleErrorCodeEnum.GEN_CODE_SYS_REQUIRE);
        }
        if (StringUtils.isEmpty(meta.getModuleName())) {
            throw new ExDeclaredException(ExKitConsoleErrorCodeEnum.GEN_CODE_MODULE_REQUIRE);
        }
        if (StringUtils.isEmpty(meta.getBizName())) {
            throw new ExDeclaredException(ExKitConsoleErrorCodeEnum.GEN_CODE_BIZ_REQUIRE);
        }
        List<GenCodeBO> list = new ArrayList<>();
        if (ExKitConsoleCodeGenEnum.FRONT_TSX_PAGE.name().equals(tag)) {
            GenCodeBO genCode = new GenCodeBO();
            genCode.setTag(tag);
            genCode.setLang("vue");
            genCode.setFileName(firstCharUpper(meta.getBizName()) + "Manage.tsx");
            genCode.setFilePath("./" + genCode.getFileName());
            genCode.setDesc("单表增删改查单页");
            genCode.setContent(genCode(meta, "web-tsx-page.tsx.vm"));
            list.add(genCode);
            list.add(getWebAPIGen(meta,tag));
            genCode = new GenCodeBO();
            genCode.setTag(tag);
            genCode.setLang("ts");
            genCode.setFileName(firstCharUpper(meta.getSysName()) + ".ts");
            genCode.setFilePath("./" + genCode.getFileName());
            genCode.setDesc("路由代码");
            genCode.setContent(genCode(meta, "web-tsx-router.vm"));
            list.add(genCode);
        }
        if (ExKitConsoleCodeGenEnum.FRONT_TEMPLATE_PAGE.name().equals(tag)) {
            GenCodeBO genCode = new GenCodeBO();
            genCode.setTag(tag);
            genCode.setLang("vue");
            genCode.setFileName(firstCharUpper(meta.getBizName()) + "Manage.vue");
            genCode.setFilePath("./" + genCode.getFileName());
            genCode.setDesc("单表列表页面");
            genCode.setContent(genCode(meta, "web-template-list.vue.vm"));
            list.add(genCode);
            genCode = new GenCodeBO();
            genCode.setTag(tag);
            genCode.setLang("vue");
            genCode.setFileName(firstCharUpper(meta.getBizName()) + "AddForm.vue");
            genCode.setFilePath("./" + genCode.getFileName());
            genCode.setDesc("单表创建页面");
            genCode.setContent(genCode(meta, "web-template-add.vue.vm"));
            list.add(genCode);
            genCode = new GenCodeBO();
            genCode.setTag(tag);
            genCode.setLang("vue");
            genCode.setFileName(firstCharUpper(meta.getBizName()) + "EditForm.vue");
            genCode.setFilePath("./" + genCode.getFileName());
            genCode.setDesc("单表修改页面");
            genCode.setContent(genCode(meta, "web-template-edit.vue.vm"));
            list.add(genCode);
            list.add(getWebAPIGen(meta,tag));
            genCode = new GenCodeBO();
            genCode.setTag(tag);
            genCode.setLang("ts");
            genCode.setFileName(firstCharUpper(meta.getSysName()) + ".ts");
            genCode.setFilePath("./" + genCode.getFileName());
            genCode.setDesc("路由代码");
            genCode.setContent(genCode(meta, "web-template-router.vm"));
            list.add(genCode);
        } else if (ExKitConsoleCodeGenEnum.SERVICE_COMMON.name().equals(tag)) {
            GenCodeBO genCode = new GenCodeBO();
            genCode.setTag(tag);
            genCode.setLang("java");
            genCode.setFileName(firstCharUpper(meta.getBizName()) + "Controller.java");
            genCode.setFilePath("./" + genCode.getFileName());
            genCode.setDesc("Controller");
            genCode.setContent(genCode(meta, "service-common-controller.java.vm"));
            list.add(genCode);
            genCode = new GenCodeBO();
            genCode.setTag(tag);
            genCode.setLang("java");
            genCode.setFileName(firstCharUpper(meta.getBizName()) + "Service.java");
            genCode.setFilePath("./" + genCode.getFileName());
            genCode.setDesc("Service");
            genCode.setContent(genCode(meta, "service-common-service.java.vm"));
            list.add(genCode);
        }
        return list;
    }

    private GenCodeBO getWebAPIGen(ModelMetaBO meta, String tag) {
        GenCodeBO genCode = new GenCodeBO();
        genCode.setTag(tag);
        genCode.setLang("typescript");
        genCode.setFileName("API.ts");
        genCode.setFilePath("./" + genCode.getFileName());
        genCode.setDesc("前端API接口");
        genCode.setContent(genCode(meta, "web-api.ts.vm"));
        return genCode;
    }

    private String genCode(ModelMetaBO meta, String templateName) {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.INPUT_ENCODING, "UTF-8");
        ve.setProperty(RuntimeConstants.OUTPUT_ENCODING, "UTF-8");
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        Template t = ve.getTemplate("template/" + templateName);
        VelocityContext ctx = new VelocityContext();
        ctx.put("modelClassName", meta.getClzName());
        String packageName = meta.getPackageName();
        if (packageName.indexOf(meta.getSysName()) >= 0) {
            packageName = packageName.substring(0, packageName.indexOf(meta.getSysName()));
            if (packageName.length() > 0 && packageName.substring(packageName.length() - 1).equals(".")) {
                packageName = packageName.substring(0, packageName.length() - 1);
            }
            packageName = packageName + "." + meta.getSysName() + "." + meta.getModuleName();
        }
        ctx.put("packageName", packageName);
        ctx.put("ObjectName", getObjectName(meta.getClzName()));
        ctx.put("objectName", firstCharLower(meta.getClzName()));
        ctx.put("SysName", firstCharUpper(meta.getSysName()));
        ctx.put("sysName", firstCharLower(meta.getSysName()));
        ctx.put("BizName", firstCharUpper(meta.getBizName()));
        ctx.put("bizName", firstCharLower(meta.getBizName()));
        ctx.put("ModuleName", firstCharUpper(meta.getModuleName()));
        ctx.put("moduleName", firstCharLower(meta.getModuleName()));
        ctx.put("fields", meta.getFields());
        ctx.put("date", TsUtil.getDate());
        StringWriter sw = new StringWriter();
        t.merge(ctx, sw);
        System.out.println(sw.toString());
        return sw.toString();
    }

    private String getObjectName(String clzName) {
        return clzName.substring(clzName.lastIndexOf(".") + 1);
    }

    private String firstCharUpper(String str) {
        return str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1);
    }

    private String firstCharLower(String str) {
        return str.substring(0, 1).toLowerCase(Locale.ROOT) + str.substring(1);
    }

}
