package cn.exsolo.bpm.console.po;

import cn.exsolo.kit.item.stereotype.ItemProvider;

/**
 * 流程审批实时状态
 * 这个状态是基于经办人视角的，例如有已提交，但没有审批中
 * @author prestolive
 */
@ItemProvider(tag = "BPM_CASE_STATUS",name="BPM-流程审批状态")
public enum FlowCaseStatusEnum {

    INIT("待提交"),
    SEND("已提交"),
    RESEND("已重新提交"),
    REJECT("已退回"),
    END("审批结束"),
    BLOCKED("挂起");

    FlowCaseStatusEnum(String label) {
        this.label = label;
    }

    private String label;

    public String getLabel() {
        return label;
    }
}
