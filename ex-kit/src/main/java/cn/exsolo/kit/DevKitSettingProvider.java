package cn.exsolo.kit;

import cn.exsolo.kit.setting.stereotype.SettingProp;
import cn.exsolo.kit.setting.stereotype.SettingProvider;

/**
 * @author prestolive
 * @date 2021/10/10
 **/
@SettingProvider("开发套件")
public class DevKitSettingProvider {

    @SettingProp(label = "是否允许前台打印错误堆栈日志", group = "日志", desc = "",inputType = SettingProp.InputType.BOOLEAN,defaultValue = "false")
    public static Boolean IS_ALLOW_WEB_ERROR_STACK;
}
