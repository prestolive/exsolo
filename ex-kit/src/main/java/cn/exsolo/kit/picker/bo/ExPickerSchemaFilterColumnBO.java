package cn.exsolo.kit.picker.bo;

/**
 * @author prestolive
 * @date 2023/8/14
 **/
public class ExPickerSchemaFilterColumnBO {

    private String label;

    private String code;

    private Boolean require;

    public ExPickerSchemaFilterColumnBO() {
        require = false;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getRequire() {
        return require;
    }

    public void setRequire(Boolean require) {
        this.require = require;
    }
}
