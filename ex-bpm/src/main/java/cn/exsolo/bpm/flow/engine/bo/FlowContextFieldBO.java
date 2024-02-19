package cn.exsolo.bpm.flow.engine.bo;

/**
 * @author prestolive
 * @date 2021/11/20
 **/
public class FlowContextFieldBO {

    private String label;

    private String parent;

    private String value;

    private String field;

    private Boolean isArray;

    private String type;

    private FlowContextFieldBO[] children;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Boolean getArray() {
        return isArray;
    }

    public void setArray(Boolean array) {
        isArray = array;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FlowContextFieldBO[] getChildren() {
        return children;
    }

    public void setChildren(FlowContextFieldBO[] children) {
        this.children = children;
    }
}
