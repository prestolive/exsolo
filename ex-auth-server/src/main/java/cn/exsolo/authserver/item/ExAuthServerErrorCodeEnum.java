package cn.exsolo.authserver.item;

import cn.exsolo.kit.item.stereotype.ItemProvider;

/**
 * @author prestolive
 * @date 2021/3/28
 **/
@ItemProvider(tag = "AUTH_SERVER_ERROR_CODE",name="授权服务-错误码",type= ItemProvider.Type.ERROR_CODE)
public enum ExAuthServerErrorCodeEnum {

    AUTH_FAILED("用户名或密码错误"),
    AUTH_RETRY_TO_MUCH("密码错误次数太多，已锁定，请稍后再试。"),
    AUTH_CAPTCHA_REQUIRE("请输入验证码"),
    AUTH_CAPTCHA_CHECK_FAIL("验证码错误");

    private String label;

    ExAuthServerErrorCodeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
