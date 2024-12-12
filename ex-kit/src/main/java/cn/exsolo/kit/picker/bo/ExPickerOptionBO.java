package cn.exsolo.kit.picker.bo;

import java.util.List;

/**
 * @author prestolive
 * @date 2021/8/13
 **/
public class ExPickerOptionBO {

    private String value;

    private String label;

    private String sub;

    private String echo1;

    private String echo2;

    private String echo3;

    private String echo4;

    private String echo5;

    private String parent;

    private int childCounts;

    private List<ExPickerOptionBO> children;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public int getChildCounts() {
        return childCounts;
    }

    public void setChildCounts(int childCounts) {
        this.childCounts = childCounts;
    }

    public List<ExPickerOptionBO> getChildren() {
        return children;
    }

    public void setChildren(List<ExPickerOptionBO> children) {
        this.children = children;
    }

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

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getEcho1() {
        return echo1;
    }

    public void setEcho1(String echo1) {
        this.echo1 = echo1;
    }

    public String getEcho2() {
        return echo2;
    }

    public void setEcho2(String echo2) {
        this.echo2 = echo2;
    }

    public String getEcho3() {
        return echo3;
    }

    public void setEcho3(String echo3) {
        this.echo3 = echo3;
    }

    public String getEcho4() {
        return echo4;
    }

    public void setEcho4(String echo4) {
        this.echo4 = echo4;
    }

    public String getEcho5() {
        return echo5;
    }

    public void setEcho5(String echo5) {
        this.echo5 = echo5;
    }
}
