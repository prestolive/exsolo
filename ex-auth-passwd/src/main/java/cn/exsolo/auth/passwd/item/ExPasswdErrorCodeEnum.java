package cn.exsolo.auth.passwd.item;

import cn.exsolo.kit.item.stereotype.ItemProvider;

/**
 * @author prestolive
 * @date 2021/4/1
 **/
@ItemProvider(tag = "EX_AUTH_PASSWOD",name="通用-密码管理",type = ItemProvider.Type.ERROR_CODE)
public enum ExPasswdErrorCodeEnum {

    SAME_PASSWORD("密码无变更，请设置新的密码"),
    RETRY_PASSWORD("字典中找到近期重复的密码，请设置新的密码"),
    OLD_PASSWORD_NO_PASS("旧密码错误");

    private String label;

    ExPasswdErrorCodeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
