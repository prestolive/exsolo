package cn.exsolo.batis.core.condition;

/**
 * Created by prestolive on 2018/2/2.
 */
public class CompareIsNullBean implements ICompareBean {

    private String field;

    public CompareIsNullBean(String field) {
        this.field = field;
    }


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
