package cn.exsolo.kit.item;

import cn.exsolo.kit.item.stereotype.Item;

/**
 * @author prestolive
 * @date 2023/4/1
 **/
@Item(tag = "EX_KIT_ERROR_CODE",name="开发套件-错误码", schema = ItemSchemaEnum.ENUM, codeField = "value", nameField = "name")
public enum ExKitErrorCodeEnum {

    DEV_NULL("不应为空"),
    DEV_EMPTY("集合对象不应没有数据");;

    private String name;

    ExKitErrorCodeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
