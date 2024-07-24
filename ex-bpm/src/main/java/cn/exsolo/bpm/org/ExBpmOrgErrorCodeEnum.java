package cn.exsolo.bpm.org;

import cn.exsolo.kit.item.stereotype.ItemProvider;

import static cn.exsolo.kit.item.stereotype.ItemProvider.Type.ERROR_CODE;

/**
 * @author prestolive
 * @date 2021/3/28
 **/
@ItemProvider(tag = "EX_BPM_ORG_ERROR_CODE", name = "BPM组织模块错误码", type = ERROR_CODE)
public enum ExBpmOrgErrorCodeEnum {

    JOB_ALREADY_RELA_ORG("岗位已存在");

    private String label;

    ExBpmOrgErrorCodeEnum(String label) {
        this.label = label;
    }


    public String getLabel() {
        return label;
    }
}
