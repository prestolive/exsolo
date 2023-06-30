package cn.exsolo.console.item;

import cn.exsolo.kit.item.ItemSchemaEnum;
import cn.exsolo.kit.item.stereotype.Item;

/**
 * @author prestolive
 * @date 2023/5/18
 **/
@Item(tag = "EX_CONSOLE_POWER",name="中台-系统安全-权限清单", schema = ItemSchemaEnum.PERMISSION, codeField = "value", nameField = "name")
public enum ExConsoleSecurityPermissionEnum {

    USER_MANAGER_VIEW("用户管理-查询权限"),
    USER_MANAGER_EDIT("用户管理-编辑权限");

    private String name;

    ExConsoleSecurityPermissionEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
