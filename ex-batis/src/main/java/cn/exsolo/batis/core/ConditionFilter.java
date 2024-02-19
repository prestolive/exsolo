package cn.exsolo.batis.core;

import cn.exsolo.batis.core.ex.BaseOrmException;

import javax.persistence.Table;

/**
 * Created by prestolive on 2018/2/1.
 */
public class ConditionFilter extends Condition {

    private String key;

    private String table;

    private String tableAlias;

    private String joinKey;


    public <T extends AbstractPO> ConditionFilter(String key, Class<T> tableJoinClz, String joinKey) {
        Table table = (Table) tableJoinClz.getAnnotation(Table.class);
        this.key = key;
        this.table = table.name();
        this.tableAlias = table.name() +"_";
        this.joinKey = joinKey;
    }

    public String getJoinKey() {
        return joinKey;
    }

    public String getKey() {
        return key;
    }

    public String getTable() {
        return table;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    @Override
    public Condition orderBy(String field){
        throw new BaseOrmException("过滤器不需要排序");
    }

    @Override
    public Condition orderBy(String field,String type) {
        throw new BaseOrmException("过滤器不需要排序");
    }

}
