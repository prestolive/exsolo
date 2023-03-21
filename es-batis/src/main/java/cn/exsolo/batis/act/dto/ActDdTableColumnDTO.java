package cn.exsolo.batis.act.dto;

/**
 * 从数据库实时查询的表结构
 * @author prestolive
 * @date 2023/3/9
 **/
public class ActDdTableColumnDTO {

    private String tableName;

    private String nullable;

    private String columnName;

    private String columnType;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }
}
