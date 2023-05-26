package cn.exsolo.console.item;

import cn.exsolo.kit.item.ItemSchemaEnum;
import cn.exsolo.kit.item.stereotype.Item;

/**
 * @author prestolive
 * @date 2023/5/17
 **/

@Item(tag = "EX_ROLE_SCHEMA",name="中台-系统角色类型", schema = ItemSchemaEnum.ENUM, codeField = "value", nameField = "name")
public enum ExRoleSchemaEnum {

    NORMAL("一般用户"),ADMIN("系统管理");

    private String name;

    ExRoleSchemaEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
