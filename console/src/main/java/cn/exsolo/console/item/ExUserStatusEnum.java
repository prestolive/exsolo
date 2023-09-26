package cn.exsolo.console.item;

import cn.exsolo.kit.item.ItemSchemaEnum;
import cn.exsolo.kit.item.stereotype.Item;

/**
 * 初始状态INIT 在登录后会触发强制修改密码
 * @author prestolive
 * @date 2021/3/31
 **/
@Item(tag = "EX_USER_STATUS",name="中台-系统用户状态", schema = ItemSchemaEnum.ENUM, codeField = "value", nameField = "name")
public enum ExUserStatusEnum {

    NORMAL("正常"), INIT("初始"),LOCKED("锁定"), DELETED("作废");

    private String name;

    ExUserStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
