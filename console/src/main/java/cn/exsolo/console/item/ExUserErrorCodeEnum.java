package cn.exsolo.console.item;

import cn.exsolo.kit.item.stereotype.ItemProvider;

/**
 * @author prestolive
 * @date 2021/4/1
 **/
@ItemProvider(tag = "EX_USER_SECURITY",name="中台-用户管理错误码",type = ItemProvider.Type.ERROR_CODE)
public enum ExUserErrorCodeEnum {

    SAME_PASSWORD("密码无变更，请设置新的密码"),
    RETRY_PASSWORD("字典中找到近期重复的密码，请设置新的密码"),
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
