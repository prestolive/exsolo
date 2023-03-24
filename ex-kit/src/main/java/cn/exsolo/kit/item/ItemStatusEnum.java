package cn.exsolo.kit.item;

import cn.exsolo.kit.item.stereotype.Item;

/**
 * @author prestolive
 * @date 2023/3/21
 **/
@Item(tag = "ITEM_STATUS",name="对象管理-状态", schema = ItemSchemaEnum.ENUM, codeField = "value", nameField = "name")
public enum ItemStatusEnum {


    NORMAL("正常"),
    BLOCK("锁定"),
    DISABLED("不可使用"),
    DELETE("删除");

    private String name;

    ItemStatusEnum(String name) {
        this.name = name;
    }

}
