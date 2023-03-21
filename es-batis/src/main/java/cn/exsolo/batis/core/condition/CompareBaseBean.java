package cn.exsolo.batis.core.condition;

/**
 * Created by prestolive on 2018/2/2.
 */
public class CompareBaseBean implements ICompareBean {

    public enum Type{
        EQ,
        NE,
        GT,
        LT,
        GE,
        LE,
        LKL,
        LKR,
        LK
    }

    public CompareBaseBean(Type type, String field, Object value) {
        this.type = type;
        this.field = field;
        this.value = value;
    }

    private Type type;

    private String field;

    private Object value;

    private boolean lower = false;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isLower() {
        return lower;
    }

    public void setLower(boolean lower) {
        this.lower = lower;
    }

    @Override
    public String toString() {
        return this.getClass().getName()+"|"+this.type+"|"+this.field+"|"+this.value;
    }
}
