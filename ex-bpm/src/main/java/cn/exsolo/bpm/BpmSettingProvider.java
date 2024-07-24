package cn.exsolo.bpm;

import cn.exsolo.basic.item.ExOrgSchemaEnum;
import cn.exsolo.kit.setting.stereotype.SettingProp;
import cn.exsolo.kit.setting.stereotype.SettingProvider;

/**
 * @author prestolive
 * @date 2021/7/10
 **/
@SettingProvider("流程引擎")
public class BpmSettingProvider {

    @SettingProp(label = "默认组织架构", group = "组织", desc = "",inputType = SettingProp.InputType.SELECTOR, dataRefCode = "EX_ORG_SCHEMA",defaultValue = "DEFAULT")
    public static ExOrgSchemaEnum BPM_DEFAULT_SCHEMA;

}
