package cn.exsolo.batis.core.condition;

/**
 * Created by prestolive on 2018/2/2.
 */
public class OrderBaseBean  {

    public enum OrderType{
        ASC,
        DESC
    }

    public OrderBaseBean(OrderType type, String field) {
        this.type = type;
        this.field = field;
    }

    private OrderType type;

    private String field;


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }
}
