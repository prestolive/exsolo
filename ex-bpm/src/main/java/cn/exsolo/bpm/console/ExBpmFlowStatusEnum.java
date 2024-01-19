package cn.exsolo.bpm.console;

import cn.exsolo.kit.item.stereotype.ItemProvider;

/**
 * @author prestolive
 * @date 2021/5/17
 **/

@ItemProvider(tag = "EX_BPM_FLOW_STATUS",name="BPM管理-流程版本状态",customAble = true)
public enum ExBpmFlowStatusEnum {

    editing("编辑中"),
    activated("已启用"),
    closed("已关闭");

    private String label;

    ExBpmFlowStatusEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
