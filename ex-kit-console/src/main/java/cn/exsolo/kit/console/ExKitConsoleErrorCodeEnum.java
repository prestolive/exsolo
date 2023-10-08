package cn.exsolo.kit.console;

import cn.exsolo.kit.item.stereotype.ItemProvider;

import static cn.exsolo.kit.item.stereotype.ItemProvider.Type.ERROR_CODE;

/**
 * @author prestolive
 * @date 2021/3/28
 **/
@ItemProvider(tag = "KIT_CONSOLE_ERROR_CODE", name = "技术中台-错误码", type = ERROR_CODE)
public enum ExKitConsoleErrorCodeEnum {

    NOT_REQUEST_MAPPING_ANNA("类%s没有RequestMapping注解，无法解析。");

    private String label;

    ExKitConsoleErrorCodeEnum(String label) {
        this.label = label;
    }


    public String getLabel() {
        return label;
    }
}
