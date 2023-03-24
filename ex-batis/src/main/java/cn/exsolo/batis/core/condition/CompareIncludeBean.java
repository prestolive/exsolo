package cn.exsolo.batis.core.condition;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by prestolive on 2018/2/2.
 */
public class CompareIncludeBean implements ICompareBean {

    private String field;

    private Object[] values;

    public CompareIncludeBean(String field, Object[] values) {
        this.field = field;
        this.values = values;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return this.getClass().getName()+"|"+this.field+"|"+ StringUtils.join(values,",");
    }
}
