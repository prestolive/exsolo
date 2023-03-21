package cn.exsolo.console.web;

import cn.exsolo.batis.act.mapper.DdlWorkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author prestolive
 * @date 2023/3/1
 **/
@Component
@RequestMapping("api/console/")
@RestController()
public class EchoController {

    @Autowired
    private DdlWorkMapper ddlWorkMapper;

//    @Autowired
//    private NacosAliveContextAware nacosAliveContextAware;

    @RequestMapping("alive")
    public Object getInstances(){
        return null;
//        return nacosAliveContextAware.findAllInstance();
    }
}
