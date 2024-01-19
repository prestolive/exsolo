package cn.exsolo.console;

import cn.exsolo.console.item.ExOrgSchemaEnum;
import cn.exsolo.kit.setting.stereotype.SettingProp;
import cn.exsolo.kit.setting.stereotype.SettingProvider;

/**
 * @author prestolive
 * @date 2023/10/10
 **/
@SettingProvider("控制台模块")
public class ConsoleSettingProvider {

    @SettingProp(label = "系统用户档案默认组织架构", group = "用户", desc = "",inputType = SettingProp.InputType.SELECTOR, dataRefCode = "EX_ORG_SCHEMA",defaultValue = "DEFAULT")
    public static ExOrgSchemaEnum USER_DEFAULT_SCHEMA;

}
