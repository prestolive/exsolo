package cn.exsolo.kit.console.api;

/**
 * @author prestolive
 * @date 2023/3/26
 **/

import cn.exsolo.kit.console.ExKitConsoleErrorCodeEnum;
import cn.exsolo.kit.dev.ApiDocService;
import cn.exsolo.kit.dev.bo.ApiDocBO;
import cn.exsolo.kit.dev.bo.ApiDocClzBO;
import cn.exsolo.kit.ex.ExErrorCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author prestolive
 * @date 2023/3/1
 **/
@Component
@RequestMapping("api/ex-kit-console/")
@RestController()
public class ApiDocController {

    private List<ApiDocClzBO> list;

    @Autowired
    private ApiDocService apiDocService;

    @RequestMapping("api-previews")
    public List<ApiDocClzBO> allController() {
        if(list==null){
            list = apiDocService.getAllController();
        }
        return list;
    }

    @RequestMapping("api-doc")
    public List<ApiDocBO> getApiDoc(String className){
        try {
            Class clz = Class.forName(className);
            RequestMapping clzAnna = (RequestMapping) clz.getAnnotation(RequestMapping.class);
            if(clzAnna==null){
                throw new ExErrorCodeException(ExKitConsoleErrorCodeEnum.NOT_REQUEST_MAPPING_ANNA,className);
            }
            return apiDocService.processClz(clz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }

}
