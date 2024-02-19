package cn.exsolo.kit.item;

import cn.exsolo.kit.item.stereotype.ItemProvider;

/**
 * @author prestolive
 * @date 2021/9/28
 **/
@ItemProvider(tag = "COMM_STATUS",name="开发套件-通用状态")
public enum ItemCommStatusEnum {

    INIT("初始"),
    AUDITING("审批中"),
    NORMAL("生效"),
    BLOCKED("停用"),
    LOCKED("锁定"),
    DELETED("作废"),
    DELETING("删除中");

    ItemCommStatusEnum(String label) {
        this.label = label;
    }

    private String label;

    public String getLabel() {
        return label;
    }
}
