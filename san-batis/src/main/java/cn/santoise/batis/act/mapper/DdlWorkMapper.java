package cn.santoise.batis.act.mapper;

import cn.santoise.batis.act.bo.ActTableBO;
import cn.santoise.batis.act.bo.ActTableIndexBO;
import cn.santoise.batis.act.dto.ActDdTableColumnDTO;
import cn.santoise.batis.act.dto.ActDdTableIndexDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author wbingy
 * @date 2023/3/7
 **/
@Mapper
public interface DdlWorkMapper {

    /**
     * 查询所有的表和字段的信息
     */
    List<ActDdTableColumnDTO> selectTableColumnAll();

    /**
     * 查询所有的表索引
     */
    List<ActDdTableIndexDTO> selectTableIndexAll();

    void createTable(ActTableBO table);
//
//    void addColumn();
//
//    void dropColumn();
//
//    void modifyColumn();
//
    void createIndex(ActTableIndexBO index);
//
//    void createUniqueIndex();
//
//    void dropIndex();
}
