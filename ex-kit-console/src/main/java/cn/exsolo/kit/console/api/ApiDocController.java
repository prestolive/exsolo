package cn.exsolo.kit.console.api;

/**
 * @author prestolive
 * @date 2023/3/26
 **/

import cn.exsolo.batis.core.Condition;
import cn.exsolo.comm.utils.EsAnnotationUtil;
import cn.exsolo.kit.dev.ApiDocService;
import cn.exsolo.kit.dev.bo.ApiDocClzBO;
import cn.exsolo.kit.item.po.ItemTagPO;
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

    @RequestMapping("api-preview")
    public List<ApiDocClzBO> allController() {
        if(list==null){
            list = apiDocService.getAllController();
        }
        return list;
    }

}
