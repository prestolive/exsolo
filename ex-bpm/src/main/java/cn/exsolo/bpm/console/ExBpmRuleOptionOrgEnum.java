package cn.exsolo.bpm.console;

import cn.exsolo.kit.item.stereotype.ItemProvider;

/**
 * @author prestolive
 * @date 2021/5/17
 **/

@ItemProvider(tag = "EX_BPM_RULE_OPTION_ORG",name="BPM管理-部门搜索方式")
public enum ExBpmRuleOptionOrgEnum {


    base_data("部门档案"),
    context_field("当前上下文部门字段");

    private String label;

    ExBpmRuleOptionOrgEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
