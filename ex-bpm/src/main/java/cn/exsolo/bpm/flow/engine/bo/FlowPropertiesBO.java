package cn.exsolo.bpm.flow.engine.bo;

/**
 * @author prestolive
 * @date 2023/10/24
 **/
public class FlowPropertiesBO {

    private String key;

    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "FlowPropertiesBO{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
