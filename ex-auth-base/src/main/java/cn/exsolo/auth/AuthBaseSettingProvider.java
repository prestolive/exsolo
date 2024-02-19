package cn.exsolo.auth;

import cn.exsolo.kit.setting.stereotype.SettingProp;
import cn.exsolo.kit.setting.stereotype.SettingProvider;

/**
 * @author prestolive
 * @date 2021/10/4
 **/
@SettingProvider("登录模块")
public class AuthBaseSettingProvider {

    @SettingProp(label = "Access Token过期时间", group = "token", desc = "",
            inputType = SettingProp.InputType.INTEGER, defaultValue = "900", suffix = "s")
    public static Integer ACCESS_TOKEN_EXPIRE_SECOND;

    @SettingProp(label = "Refresh Token过期时间", group = "token", desc = "在有效期内可以通过Refresh Token获取新的Access Token",
            inputType = SettingProp.InputType.INTEGER, defaultValue = "259200", suffix = "s")
    public static Integer REFRESH_TOKEN_EXPIRE_SECOND;
}
