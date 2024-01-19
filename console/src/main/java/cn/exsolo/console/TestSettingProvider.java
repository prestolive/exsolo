package cn.exsolo.console;

import cn.exsolo.kit.setting.stereotype.SettingProp;
import cn.exsolo.kit.setting.stereotype.SettingProvider;

import java.math.BigDecimal;

/**
 * @author prestolive
 * @date 2023/10/4
 **/
@SettingProvider("测试")
public class TestSettingProvider {

    @SettingProp(label = "url", group = "group1", desc = "测试描述",
            inputType = SettingProp.InputType.STRING, defaultValue = "900", prefix = "http://")
    public static String URL_111111111111;

    @SettingProp(label = "测试字符串参数", group = "group1", desc = "测试描述",
            inputType = SettingProp.InputType.STRING, defaultValue = "900", suffix = "吨")
    public static String testSTRDDDD;

    @SettingProp(label = "测试switch", group = "group2", desc = "测试描述",
            inputType = SettingProp.InputType.BOOLEAN, defaultValue = "false")
    public static Boolean testboolean;

    @SettingProp(label = "测试数值", group = "group2", desc = "测试描述",
            inputType = SettingProp.InputType.DECIMAL, defaultValue = "0.01")
    public static BigDecimal decimal;

    @SettingProp(label = "测试picker", group = "group2", desc = "测试描述",
            inputType = SettingProp.InputType.PICKER, dataRefCode = "DEFAULT_USER_PICKER", isRequireInInit = false, defaultValue = "")
    public static String pickeridxx;

    @SettingProp(label = "测试日期", group = "group2", desc = "测试描述",
            inputType = SettingProp.InputType.DATE, isRequireInInit = false, defaultValue = "")
    public static String date;

    @SettingProp(label = "测试时间", group = "group3", desc = "测试描述",
            inputType = SettingProp.InputType.DATETIME, isRequireInInit = false, defaultValue = "")
    public static String ts;

    @SettingProp(label = "测试年", group = "group3", desc = "测试描述",
            inputType = SettingProp.InputType.YEAR, isRequireInInit = false, defaultValue = "")
    public static String year;

    @SettingProp(label = "测试月", group = "group3", desc = "测试描述",
            inputType = SettingProp.InputType.MONTH, isRequireInInit = false, defaultValue = "")
    public static String month;

}
