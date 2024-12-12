package cn.exsolo.bpm.console;

import cn.exsolo.kit.item.stereotype.ItemProvider;

/**
 * @author prestolive
 * @date 2021/5/17
 **/

@ItemProvider(tag = "EX_BPM_RULE_ORG_FILTER_SCOPE",name="BPM管理-部门定位范围")
public enum ExBpmRuleOrgFilterScopeEnum {

    none("不需要部门过滤"),
    current("指定部门"),
    current_and_upper("指定部门及上级"),
    only_upper("指定部门上级（不含当指定部门）");

    private String label;

    ExBpmRuleOrgFilterScopeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
