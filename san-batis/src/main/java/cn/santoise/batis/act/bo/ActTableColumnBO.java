package cn.santoise.batis.act.bo;

/**
 * @author wbingy
 * @date 2023/3/8
 **/
public class ActTableColumnBO {

    private String name;

    private String datatype;

    private Boolean isPrimary;

    private Boolean nullable;

    private ActSuggestDatatypeBO actSuggestDatatypeBO;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public ActSuggestDatatypeBO getDatatypeUnderstandingBO() {
        return actSuggestDatatypeBO;
    }

    public void setDatatypeUnderstandingBO(ActSuggestDatatypeBO actSuggestDatatypeBO) {
        this.actSuggestDatatypeBO = actSuggestDatatypeBO;
    }

    public Boolean getPrimary() {
        return isPrimary;
    }

    public void setPrimary(Boolean primary) {
        isPrimary = primary;
    }

    public Boolean getNullable() {
        return nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public ActSuggestDatatypeBO getActSuggestDatatypeBO() {
        return actSuggestDatatypeBO;
    }

    public void setActSuggestDatatypeBO(ActSuggestDatatypeBO actSuggestDatatypeBO) {
        this.actSuggestDatatypeBO = actSuggestDatatypeBO;
    }
}
