package cn.exsolo.kit.picker;

import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.kit.picker.bo.ExPickerOptionBO;

/**
 * @author prestolive
 * @date 2023/8/13
 **/
public interface IPicker {

    String getCode();

    PageObject<ExPickerOptionBO> find(Pagination pagination, String keyword, Condition cond);

    ExPickerOptionBO get(String id);

}
