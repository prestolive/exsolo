package cn.exsolo.bpm.console;

import cn.exsolo.kit.item.stereotype.ItemProvider;

import static cn.exsolo.kit.item.stereotype.ItemProvider.Type.ERROR_CODE;

@ItemProvider(tag = "BPM_CONSOLE_ERROR_CODE", name = "BPM管理-错误码", type = ERROR_CODE)
public enum ExBpmConsoleErrorCodeEnum {

    CODE_ALREADY_EXISTS("流程编码%s已存在！"),

    EDITING_VERSION_REPEAT("流程编码%s已有编辑中的版本，请在编辑中的版本修改！");

    private String label;

    ExBpmConsoleErrorCodeEnum(String label) {
        this.label = label;
    }


    public String getLabel() {
        return label;
    }
}
