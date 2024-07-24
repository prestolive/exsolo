package cn.exsolo.kit.console;


import cn.exsolo.kit.item.stereotype.ItemProvider;

import static cn.exsolo.kit.item.stereotype.ItemProvider.Type.NORMAL;

/**
 * @author prestolive
 * @date 2021/3/28
 **/
@ItemProvider(tag = "KIT_CONSOLE_CODE_GEN", name = "代码生成模板", type = NORMAL)
public enum ExKitConsoleCodeGenEnum {

    FRONT_TSX_PAGE("TSX单表增删改查单页"),
    FRONT_TEMPLATE_PAGE("VueTemplate单表增删改查"),
    SERVICE_COMMON("通用后端");

    private String label;

    ExKitConsoleCodeGenEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
