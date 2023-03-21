package cn.exsolo.batis.core.condition;

/**
 * Created by prestolive on 2018/2/2.
 */
public class CompareIsNotNullBean implements ICompareBean {


    private String field;

    public CompareIsNotNullBean(String field) {
        this.field = field;
    }


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }


    @Override
    public String toString() {
        return this.getClass().getName()+"|"+this.field;
    }

}
