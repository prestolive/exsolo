package cn.exsolo.kit.console.api;

/**
 * @author prestolive
 * @date 2021/3/26
 **/

import cn.exsolo.auth.shiro.ext.stereotype.AccessEdit;
import cn.exsolo.auth.shiro.ext.stereotype.AccessProvider;
import cn.exsolo.auth.shiro.ext.stereotype.AccessView;
import cn.exsolo.comm.ex.ExDeclaredException;
import cn.exsolo.kit.console.ExKitConsoleErrorCodeEnum;
import cn.exsolo.kit.dev.ApiDocGenerateCodeService;
import cn.exsolo.kit.dev.ModelDocService;
import cn.exsolo.kit.dev.ModelGenCodeService;
import cn.exsolo.kit.dev.bo.DevClzBO;
import cn.exsolo.kit.dev.bo.GenCodeBO;
import cn.exsolo.kit.dev.bo.ModelMetaBO;
import cn.exsolo.springmvcext.stereotype.RequestJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Table;
import java.util.List;

/**
 * @author prestolive
 * @date 2021/3/1
 **/
@Component
@RequestMapping("api/ex-kit-console/")
@RestController()
@AccessProvider(module = "kit",node = "model",label = "开发套件-Model管理")
public class ModelDocController {

    private List<DevClzBO> list;

    @Autowired
    private ModelDocService modelDocService;

    @Autowired
    private ApiDocGenerateCodeService apiDocGenerateCodeService;

    @Autowired
    private ModelGenCodeService modelGenCodeService;

    @AccessView
    @RequestMapping(value = "model-previews",method = RequestMethod.POST)
    public List<DevClzBO> allController() {
        if(list==null){
            list = modelDocService.getModels();
        }
        return list;
    }

    @AccessView
    @RequestMapping(value="model-doc",method = RequestMethod.POST)
    public ModelMetaBO getModelDoc(String className){
        try {
            Class clz = Class.forName(className);
            Table clzAnna = (Table) clz.getAnnotation(Table.class);
            if(clzAnna==null){
                throw new ExDeclaredException(ExKitConsoleErrorCodeEnum.NOT_REQUEST_MAPPING_ANNA,className);
            }
            return modelDocService.processClz(clz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    @AccessEdit
    @RequestMapping(value="model-gen-code",method = RequestMethod.POST)
    public List<GenCodeBO> genCode(@RequestJSON ModelMetaBO meta, @RequestJSON String tag){
        //生成代码
        List list =  modelGenCodeService.gen(meta,tag);
        //如果生成成功就保存配置
        modelDocService.saveModelMetaDev(meta);
        return list;
    }

}
