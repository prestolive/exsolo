package cn.exsolo.kit.dev.bo;

/**
 * @Table 注解反向得到的字段信息
 * @author prestolive
 * @date 2024/6/26
 **/
public class ModelFieldBO {

    private String code;

    private String name;

    private String label;

    private String javaType;

    private String dbType;

    private String jsType;

    private String inputType;

    private String inputArg;

    private Boolean inTable = true;

    private Boolean inCondition = true;

    private Boolean inForm = true;

    private Boolean editAble = true;

    private Boolean required = false;

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getInputArg() {
        return inputArg;
    }

    public void setInputArg(String inputArg) {
        this.inputArg = inputArg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getJsType() {
        return jsType;
    }

    public void setJsType(String jsType) {
        this.jsType = jsType;
    }

    public Boolean getInTable() {
        return inTable;
    }

    public void setInTable(Boolean inTable) {
        this.inTable = inTable;
    }

    public Boolean getInCondition() {
        return inCondition;
    }

    public void setInCondition(Boolean inCondition) {
        this.inCondition = inCondition;
    }

    public Boolean getInForm() {
        return inForm;
    }

    public void setInForm(Boolean inForm) {
        this.inForm = inForm;
    }

    public Boolean getEditAble() {
        return editAble;
    }

    public void setEditAble(Boolean editAble) {
        this.editAble = editAble;
    }
}
