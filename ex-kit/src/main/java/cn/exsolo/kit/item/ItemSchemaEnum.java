package cn.exsolo.kit.item;

import cn.exsolo.kit.item.stereotype.Item;

/**
 * @author prestolive
 * @date 2021/3/16
 **/
@Item(tag = "ITEM_SCHEMA",name="对象管理-对象类型", schema = ItemSchemaEnum.ENUM, codeField = "value", nameField = "name")
public enum ItemSchemaEnum {

    ENUM("枚举"),
    PERMISSION("权限"),
    BASE_DATA("基础数据"),
    TREE("树"),
    ERROR_CODE("错误码");

    private String name;

    ItemSchemaEnum(String name) {
        this.name = name;
    }
}
