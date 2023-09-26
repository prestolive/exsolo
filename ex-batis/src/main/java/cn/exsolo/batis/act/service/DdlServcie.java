package cn.exsolo.batis.act.service;

import cn.exsolo.batis.act.bo.ActTableBO;
import cn.exsolo.batis.act.bo.ActTableColumnBO;
import cn.exsolo.batis.act.bo.ActTableIndexBO;
import cn.exsolo.batis.act.dto.ActDdTableColumnDTO;
import cn.exsolo.batis.act.dto.ActDdTableIndexDTO;
import cn.exsolo.batis.act.mapper.DdlWorkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author prestolive
 * @date 2021/3/9
 **/
@Service
public class DdlServcie {

    @Autowired
    private DdlWorkMapper ddlWorkMapper;

    public List<ActDdTableColumnDTO> selectTableColumnAll() {
        return ddlWorkMapper.selectTableColumnAll();
    }

    public List<ActDdTableIndexDTO> selectTableIndexAll() {
        return ddlWorkMapper.selectTableIndexAll();
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public void createTable(ActTableBO table) {
        ddlWorkMapper.createTable(table);
        for (ActTableIndexBO index : table.getIndexes()) {
            ddlWorkMapper.createIndex(index);
        }
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public void modifyTable(ActTableBO table, List<ActDdTableColumnDTO> dbColumns, List<ActDdTableIndexDTO> dbIndexes) {
        //补充缺失字段
        List<ActTableColumnBO> todos = table.getColumns().stream().filter(todo -> {
            return dbColumns.stream().noneMatch(exist -> exist.getColumnName().equalsIgnoreCase(todo.getName()));
        }).collect(Collectors.toList());
        for (ActTableColumnBO column : todos) {
            ddlWorkMapper.addColumn(column);
        }
        //FIXME 修改字段 暂时不做
        //FIXME 检测到数据库有，但是对象没有的情况下暂时不处理
        //索引
        //只处理同名的即物理存在不同名的不删，同名的字段不一样（包含顺序不一样）就删掉重建
        //1.索引不存在
        List<ActTableIndexBO> todoIndexes = table.getIndexes().stream().filter(todo -> {
            return dbIndexes.stream().noneMatch(exist -> exist.getIndexName().equalsIgnoreCase(todo.getName()));
        }).collect(Collectors.toList());
        for (ActTableIndexBO index : todoIndexes) {
            ddlWorkMapper.createIndex(index);
        }
        //2.索引存在但是字段不一样
        todoIndexes = table.getIndexes().stream().filter(todo -> {
            return dbIndexes.stream().anyMatch(exist -> {
                return
                        exist.getIndexName().equalsIgnoreCase(todo.getName())
                                &&  ( !exist.getIndexFields().equals(todo.getFields())
                                        ||!(exist.getUnique()&&todo.isUnique())
                                );
            });
        }).collect(Collectors.toList());
        for (ActTableIndexBO index : todoIndexes) {
            ddlWorkMapper.dropIndex(index);
            ddlWorkMapper.createIndex(index);
        }
    }
}
