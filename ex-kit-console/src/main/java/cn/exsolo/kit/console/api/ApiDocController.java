package cn.exsolo.kit.console.api;

/**
 * @author prestolive
 * @date 2023/3/26
 **/

import cn.exsolo.kit.console.ExKitConsoleErrorCodeEnum;
import cn.exsolo.kit.dev.ApiDocGenerateCodeService;
import cn.exsolo.kit.dev.ApiDocService;
import cn.exsolo.kit.dev.bo.ApiDocBO;
import cn.exsolo.kit.dev.bo.ApiDocClzBO;
import cn.exsolo.kit.dev.utils.FileUtil;
import cn.exsolo.comm.ex.ExDeclaredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
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

    @Autowired
    private ApiDocGenerateCodeService apiDocGenerateCodeService;

    @RequestMapping(value = "api-previews",method = RequestMethod.POST)
    public List<ApiDocClzBO> allController() {
        if(list==null){
            list = apiDocService.getAllController();
        }
        return list;
    }

    @RequestMapping(value="api-doc",method = RequestMethod.POST)
    public List<ApiDocBO> getApiDoc(String className){
        try {
            Class clz = Class.forName(className);
            RequestMapping clzAnna = (RequestMapping) clz.getAnnotation(RequestMapping.class);
            if(clzAnna==null){
                throw new ExDeclaredException(ExKitConsoleErrorCodeEnum.NOT_REQUEST_MAPPING_ANNA,className);
            }
            return apiDocService.processClz(clz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }


    @RequestMapping(value="api-doc2code",method = RequestMethod.POST)
    public void downloadApiTsCode(HttpServletRequest request,HttpServletResponse response, String className) {
        String content = apiDocGenerateCodeService.generateClass(className);
        FileUtil.download(response,new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)),"API.ts","text/plain");
    }

    @RequestMapping(value="api-docs2code",method = RequestMethod.POST)
    public void downloadApiTsCodeModule(HttpServletRequest request,HttpServletResponse response, String module) {
        String content = apiDocGenerateCodeService.generateModule(module);
        FileUtil.download(response,new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)),"API.ts","text/plain");
    }


}
