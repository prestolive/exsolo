package cn.exsolo.console.pub.vo;

/**
 * 通用的item vo
 * @author prestolive
 * @date 2023/10/8
 **/
public class CommItemVO {

    private String label;

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
