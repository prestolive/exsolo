package cn.exsolo.basic.item;

import cn.exsolo.kit.item.stereotype.ItemProvider;

/**
 * @author prestolive
 * @date 2021/4/1
 **/
@ItemProvider(tag = "EX_USER_SECURITY",name="中台-用户管理错误码",type = ItemProvider.Type.ERROR_CODE)
public enum ExUserErrorCodeEnum {

    USER_ALREADY_EXISTS("用户编码 %s 已存在"),
    ROLE_ALREADY_EXISTS("角色名称 %s 已存在");

    private String label;

    ExUserErrorCodeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
