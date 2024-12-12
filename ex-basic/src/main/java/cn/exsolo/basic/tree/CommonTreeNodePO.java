package cn.exsolo.basic.tree;

import cn.exsolo.batis.core.AbstractPO;

import javax.persistence.Column;
import java.util.List;

/**
 * @author prestolive
 * @date 2021/5/28
 **/
public abstract class CommonTreeNodePO extends AbstractPO {

    @Column(name = "genus",nullable = false,length = 24,columnDefinition = "varchar(64)")
    private String genus;

    @Column(name = "innerCode",length = 128,columnDefinition = "varchar(128)")
    private String innerCode;

    @Column(name = "deep",length = 2,columnDefinition = "int2")
    private Integer deep;

    @Column(name = "parentId",length = 24,columnDefinition = "char(24)")
    private String parentId;

    @Column(name = "sortNo",columnDefinition = "int(2)")
    private Integer sortNo;

    @Column(name = "childCounts",columnDefinition = "int(2)")
    private Integer childCounts;


    private List<CommonTreeNodePO> paths;


    public List<CommonTreeNodePO> getPaths() {
        return paths;
    }

    public void setPaths(List<CommonTreeNodePO> paths) {
        this.paths = paths;
    }

    public String getInnerCode() {
        return innerCode;
    }

    public void setInnerCode(String innerCode) {
        this.innerCode = innerCode;
    }

    public Integer getDeep() {
        return deep;
    }

    public void setDeep(Integer deep) {
        this.deep = deep;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public Integer getChildCounts() {
        return childCounts;
    }

    public void setChildCounts(Integer childCounts) {
        this.childCounts = childCounts;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }
}
