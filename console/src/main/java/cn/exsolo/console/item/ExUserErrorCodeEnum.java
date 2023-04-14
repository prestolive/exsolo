package cn.exsolo.console.item;

import cn.exsolo.kit.item.ItemSchemaEnum;
import cn.exsolo.kit.item.stereotype.Item;

/**
 * @author prestolive
 * @date 2023/4/1
 **/
@Item(tag = "EX_USER_SECURITY",name="中台-错误码", schema = ItemSchemaEnum.ENUM, codeField = "value", nameField = "name")
public enum ExUserErrorCodeEnum {

    SAME_PASSWORD("密码无变更，请设置新的密码"),
    RETRY_PASSWORD("字典中找到近期重复的密码，请设置新的密码"),
    USER_ALREADY_EXISTS("用户编码 %s 已存在");

    private String name;

    ExUserErrorCodeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
