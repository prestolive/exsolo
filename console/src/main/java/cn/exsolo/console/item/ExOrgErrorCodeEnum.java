package cn.exsolo.console.item;

import cn.exsolo.kit.item.ItemSchemaEnum;
import cn.exsolo.kit.item.stereotype.Item;

/**
 * 组织管理
 * @author prestolive
 * @date 2021/4/1
 **/
@Item(tag = "EX_USER_SECURITY",name="中台-组织管理错误码", schema = ItemSchemaEnum.ERROR_CODE, codeField = "value", nameField = "name")
public enum ExOrgErrorCodeEnum {

    SCHEMA_NAME_ALREADY_EXISTS("组织类型名称 %s 已存在"),
    SCHEMA_CODE_ALREADY_EXISTS("组织类型编码 %s 已存在");

    private String name;

    ExOrgErrorCodeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
