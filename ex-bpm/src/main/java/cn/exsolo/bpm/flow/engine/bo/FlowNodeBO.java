package cn.exsolo.bpm.flow.engine.bo;

/**
 * @author prestolive
 * @date 2021/10/24
 **/
public class FlowNodeBO {

    private String id;

    private String label;

    private FlowNodeType type;

    private Integer x;

    private Integer y;


    private FlowPropertiesBO[] properties;

    public FlowPropertiesBO[] getProperties() {
        return properties;
    }

    public void setProperties(FlowPropertiesBO[] properties) {
        this.properties = properties;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public FlowNodeType getType() {
        return type;
    }

    public void setType(FlowNodeType type) {
        this.type = type;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
