package cn.exsolo.bpm.org;

import cn.exsolo.kit.item.stereotype.ItemProvider;

import static cn.exsolo.kit.item.stereotype.ItemProvider.Type.ERROR_CODE;

/**
 * @author prestolive
 * @date 2021/3/28
 **/
@ItemProvider(tag = "EX_BPM_ORG_ERROR_CODE", name = "BPM组织模块错误码", type = ERROR_CODE)
public enum ExBpmOrgErrorCodeEnum {

    JOB_ALREADY_RELA_ORG("岗位已存在"),
    JOB_CODE_DUPLICATE("岗位编码%s已存在"),
    ORG_USER_JOB_RELA_MUST_DELETE_FROM_JOB("该用户组织关联来自于岗位设置，请从岗位设置中取消。");

    private String label;

    ExBpmOrgErrorCodeEnum(String label) {
        this.label = label;
    }


    public String getLabel() {
        return label;
    }
}
