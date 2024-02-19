package cn.exsolo.basic.web;

import cn.exsolo.auth.shiro.ext.stereotype.AccessConfig;
import cn.exsolo.auth.shiro.ext.stereotype.AccessProvider;
import cn.exsolo.auth.shiro.ext.stereotype.AccessView;
import cn.exsolo.kit.setting.ExSettingService;
import cn.exsolo.kit.setting.vo.ExSettingInstanceVO;
import cn.exsolo.kit.setting.vo.ExSettingProviderVO;
import cn.exsolo.springmvcext.stereotype.RequestJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author prestolive
 * @date 2021/10/4
 **/
@Component
@RequestMapping("api/ex-basic/setting/")
@RestController()
@AccessProvider(module = "sys", node = "setting", label = "系统-系统设置")
public class SettingController {

    @Autowired
    private ExSettingService exSettingService;

    @AccessView
    @RequestMapping(path = "modules", method = RequestMethod.POST)
    public List<ExSettingProviderVO> modules() {
        return exSettingService.getAllSettingProviders();
    }


    @AccessView
    @RequestMapping(path = "props", method = RequestMethod.POST)
    public List<ExSettingInstanceVO> props(@RequestJSON String clzName) {
        return exSettingService.getSettingPropsByClz(clzName);
    }

    @AccessConfig
    @RequestMapping(path = "set", method = RequestMethod.POST)
    public void set(@RequestJSON String clzName,@RequestJSON String fieldName,@RequestJSON String propValue) {
        exSettingService.setValue(clzName,fieldName,propValue);
    }


}
