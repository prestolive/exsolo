package cn.exsolo.console.web;

import cn.exsolo.auth.shiro.ext.stereotype.AccessProvider;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.console.picker.PickerFactory;
import cn.exsolo.console.security.po.RolePO;
import cn.exsolo.kit.picker.IPicker;
import cn.exsolo.kit.picker.bo.ExPickerOptionBO;
import cn.exsolo.springmvcext.stereotype.RequestJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author prestolive
 * @date 2023/8/23
 **/

@Component
@RequestMapping("api/console/")
@RestController()
public class PickerController {

    @Autowired
    private PickerFactory pickerFactory;

    @RequestMapping(path = "picker/find", method = RequestMethod.POST)
    public PageObject<ExPickerOptionBO> find(
            @RequestJSON String code,
            @RequestJSON String keyword,
            @RequestJSON Condition cond,
            @RequestJSON Pagination pagination) {
        IPicker picker = pickerFactory.getPicker(code);
        return picker.find(pagination,keyword,cond);
    }

    @RequestMapping(path = "picker/get", method = RequestMethod.POST)
    public ExPickerOptionBO get(
            @RequestJSON String code,
            @RequestJSON String id){
        IPicker picker = pickerFactory.getPicker(code);
        return picker.get(id);
    }


}
