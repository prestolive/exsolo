package cn.exsolo.batis.act.bo;

import java.util.List;

/**
 * @author prestolive
 * @date 2023/3/8
 **/
public class ActTableBO {

    private String name;

    private String primaryKey;

    private List<ActTableColumnBO> columns;

    private List<ActTableIndexBO> indexes;

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ActTableColumnBO> getColumns() {
        return columns;
    }

    public void setColumns(List<ActTableColumnBO> columns) {
        this.columns = columns;
    }

    public List<ActTableIndexBO> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<ActTableIndexBO> indexes) {
        this.indexes = indexes;
    }
}
