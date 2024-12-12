package cn.exsolo.console.web;

import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.comm.ex.ExDeclaredException;
import cn.exsolo.console.ExPubDataErrorCodeEnum;
import cn.exsolo.console.picker.PickerFactory;
import cn.exsolo.console.pub.service.ItemQueryService;
import cn.exsolo.console.pub.vo.CommItemVO;
import cn.exsolo.kit.picker.IPicker;
import cn.exsolo.kit.picker.ITreePicker;
import cn.exsolo.kit.picker.bo.ExPickerOptionBO;
import cn.exsolo.springmvcext.stereotype.RequestJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author prestolive
 * @date 2021/8/23
 **/

@Component
@RequestMapping("api/console/")
@RestController()
public class PubDataController {

    @Autowired
    private PickerFactory pickerFactory;

    @Autowired
    private ItemQueryService itemQueryService;

    @RequestMapping(path = "picker/find", method = RequestMethod.POST)
    public PageObject<ExPickerOptionBO> find(
            @RequestJSON String code,
            @RequestJSON String keyword,
            @RequestJSON Condition cond,
            @RequestJSON Pagination pagination) {
        IPicker picker = pickerFactory.getPicker(code);
        return picker.find(pagination, keyword, cond);
    }

    @RequestMapping(path = "picker/children", method = RequestMethod.POST)
    public List<ExPickerOptionBO> children(
            @RequestJSON String code,
            @RequestJSON String parentId) {
        IPicker picker = pickerFactory.getPicker(code);
        if(picker instanceof ITreePicker){
            ITreePicker treePicker = (ITreePicker) picker;
            return treePicker.getNodes(parentId);
        }
        throw new ExDeclaredException(ExPubDataErrorCodeEnum.NO_TREE_PICKER);
    }

    @RequestMapping(path = "picker/get", method = RequestMethod.POST)
    public List<ExPickerOptionBO> get(
            @RequestJSON String code,
            @RequestJSON String[] ids) {
        IPicker picker = pickerFactory.getPicker(code);
        List<ExPickerOptionBO> list = picker.getList(Arrays.asList(ids));
        return list;
    }


    @RequestMapping(path = "select/list", method = RequestMethod.POST)
    public List<CommItemVO> selectList(@RequestJSON String tag) {
        return itemQueryService.getSelectList(tag);
    }


}
