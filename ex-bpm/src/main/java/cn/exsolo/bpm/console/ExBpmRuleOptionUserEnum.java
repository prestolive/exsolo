package cn.exsolo.bpm.console;

import cn.exsolo.kit.item.stereotype.ItemProvider;

/**
 * @author prestolive
 * @date 2021/5/17
 **/

@ItemProvider(tag = "EX_BPM_RULE_OPTION_USER",name="BPM管理-员工搜索方式")
public enum ExBpmRuleOptionUserEnum {
    base_data("员工档案"),
    context_field("当前上下文员工字段");

    private String label;

    ExBpmRuleOptionUserEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
