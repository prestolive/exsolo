package cn.exsolo.console.item;

import cn.exsolo.kit.item.stereotype.ItemProvider;

/**
 * 组织管理
 * @author prestolive
 * @date 2021/4/1
 **/
@ItemProvider(tag = "EX_ORG_ERROR_CODE",name="中台-组织管理错误码", type = ItemProvider.Type.ERROR_CODE)
public enum ExOrgErrorCodeEnum {

    SCHEMA_NAME_ALREADY_EXISTS("组织类型名称 %s 已存在"),
    SCHEMA_CODE_ALREADY_EXISTS("组织类型编码 %s 已存在"),
    ORG_NAME_ALREADY_EXISTS("组织名称 %s 在同级下已存在");

    private String label;

    ExOrgErrorCodeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
