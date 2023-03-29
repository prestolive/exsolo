package cn.exsolo.kit.console;

import cn.exsolo.kit.item.ItemSchemaEnum;
import cn.exsolo.kit.item.stereotype.Item;

/**
 * @author prestolive
 * @date 2023/3/28
 **/
@Item(tag = "KIT_CONSOLE_ERROR_CODE",name="技术中台-错误码", schema = ItemSchemaEnum.ENUM, codeField = "value", nameField = "name")
public enum ExKitConsoleErrorCodeEnum {

    NOT_REQUEST_MAPPING_ANNA("类%s没有RequestMapping注解，无法解析。");

    private String name;

    ExKitConsoleErrorCodeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
