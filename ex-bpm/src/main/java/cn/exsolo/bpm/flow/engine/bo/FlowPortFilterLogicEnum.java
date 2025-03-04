package cn.exsolo.bpm.flow.engine.bo;

import cn.exsolo.kit.item.stereotype.ItemProvider;

/**
 * @author prestolive
 * @date 2025/1/16
 **/

@ItemProvider(tag = "BPM_PORT_FILTER_LOGIC",name="BPM-流转过滤条件类型")
public enum FlowPortFilterLogicEnum {

    INCLUDE_ALL("满足以下所有条件"),
    INCLUDE_ANY("满足以下任意条件"),
    EXCLUDE_ALL("不包含以下所有条件"),
    EXCLUDE_ANY("不包含以下任意条件"),
    EXECUTE("条件");

    FlowPortFilterLogicEnum(String label) {
        this.label = label;
    }

    private String label;

    public String getLabel() {
        return label;
    }
}
