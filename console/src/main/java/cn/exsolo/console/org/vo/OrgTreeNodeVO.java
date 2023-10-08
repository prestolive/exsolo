package cn.exsolo.console.org.vo;

import java.util.List;

/**
 * 用于界面展示的树形对象
 * @author prestolive
 * @date 2023/9/26
 **/
public class OrgTreeNodeVO {

    private String value;

    private String label;

    private String code;

    private String innerCode;

    private String parentId;

    private Integer sortNo;

    private Integer childCounts;

    private List<OrgTreeNodeVO> children;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInnerCode() {
        return innerCode;
    }

    public void setInnerCode(String innerCode) {
        this.innerCode = innerCode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getChildCounts() {
        return childCounts;
    }

    public void setChildCounts(Integer childCounts) {
        this.childCounts = childCounts;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public List<OrgTreeNodeVO> getChildren() {
        return children;
    }

    public void setChildren(List<OrgTreeNodeVO> children) {
        this.children = children;
    }
}
