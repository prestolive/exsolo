package cn.exsolo.authserver.item;

import cn.exsolo.kit.item.ItemSchemaEnum;
import cn.exsolo.kit.item.stereotype.Item;

/**
 * @author prestolive
 * @date 2023/3/28
 **/
@Item(tag = "AUTH_SERVER_ERROR_CODE",name="授权服务-错误码", schema = ItemSchemaEnum.ERROR_CODE, codeField = "value", nameField = "name")
public enum ExAuthServerErrorCodeEnum {

    AUTH_FAILED("用户名或密码错误");

    private String name;

    ExAuthServerErrorCodeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
