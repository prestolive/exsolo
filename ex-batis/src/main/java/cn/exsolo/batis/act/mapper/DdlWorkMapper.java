package cn.exsolo.batis.act.mapper;

import cn.exsolo.batis.act.bo.ActTableBO;
import cn.exsolo.batis.act.bo.ActTableColumnBO;
import cn.exsolo.batis.act.bo.ActTableIndexBO;
import cn.exsolo.batis.act.dto.ActDdTableColumnDTO;
import cn.exsolo.batis.act.dto.ActDdTableIndexDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author prestolive
 * @date 2021/3/7
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

    void addColumn(ActTableColumnBO column);

    //    void dropColumn();
//    void modifyColumn();
    void dropIndex(ActTableIndexBO index);

    void createIndex(ActTableIndexBO index);
//
//    void createUniqueIndex();

}
