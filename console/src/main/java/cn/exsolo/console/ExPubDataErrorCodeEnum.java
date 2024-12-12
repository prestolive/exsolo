package cn.exsolo.console;

/**
 * 公共数据错误类
 * @author prestolive
 * @date 2021/4/1
 **/
public enum ExPubDataErrorCodeEnum {

    NO_TREE_PICKER("非树形管理Picker");

    private String label;

    ExPubDataErrorCodeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
