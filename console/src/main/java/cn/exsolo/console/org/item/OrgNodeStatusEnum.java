package cn.exsolo.console.org.item;

import cn.exsolo.kit.item.ItemSchemaEnum;
import cn.exsolo.kit.item.stereotype.Item;

/**
 * @author prestolive
 * @date 2023/9/26
 **/
@Item(tag = "ORG_NODE_STATUS",name="组织管理-节点状态", schema = ItemSchemaEnum.ENUM, codeField = "value", nameField = "label")
public enum OrgNodeStatusEnum {

    NORMAL("启用"),
    PENDING("停用"),
    RIP("作废"),
    DELETING("删除中");

    OrgNodeStatusEnum(String label) {
        this.label = label;
    }

    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
