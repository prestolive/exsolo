package cn.exsolo.kit.item;

import cn.exsolo.kit.item.stereotype.Item;

/**
 * @author prestolive
 * @date 2023/3/16
 **/
@Item(tag = "ITEM_ORIGIN",name="对象管理-来源", schema = ItemSchemaEnum.ENUM, codeField = "value", nameField = "name")
public enum ItemOriginEnum {

    SOURCE("源代码定义"),
    CUSTOM("用户自定义"),
    BASE_DATA("基础数据"),
    ERROR_CODE("错误码");

    private String name;

    ItemOriginEnum(String name) {
        this.name = name;
    }
}
