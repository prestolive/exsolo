package cn.exsolo.authserver.item;

import cn.exsolo.kit.item.ItemSchemaEnum;
import cn.exsolo.kit.item.stereotype.Item;

/**
 * @author prestolive
 * @date 2021/3/28
 **/
@Item(tag = "AUTH_SERVER_ERROR_CODE",name="授权服务-错误码", schema = ItemSchemaEnum.ERROR_CODE, codeField = "value", nameField = "name")
public enum ExAuthServerErrorCodeEnum {

    AUTH_FAILED("用户名或密码错误"),
    AUTH_RETRY_TO_MUCH("密码错误次数太多，已锁定，请稍后再试。"),
    AUTH_CAPTCHA_REQUIRE("请输入验证码"),
    AUTH_CAPTCHA_CHECK_FAIL("验证码错误");


    private String name;

    ExAuthServerErrorCodeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
