package cn.exsolo.kit.picker;

import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.kit.picker.bo.ExPickerOptionBO;

import java.util.List;

/**
 * @author prestolive
 * @date 2021/8/13
 **/
public interface IPicker {

    String getCode();

    PageObject<ExPickerOptionBO> find(Pagination pagination, String keyword, Condition cond);

    ExPickerOptionBO getSingle(String id);

    List<ExPickerOptionBO> getList(List<String> ids);

}
