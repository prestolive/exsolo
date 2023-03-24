package cn.exsolo.batis.core.ext;



import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * Created by prestolive on 2018/7/25.
 */
@Mapper
public interface AdapterMapper {

    @Select({"${sql}"})
    List executeQuery(Map map);


    @Update({"${sql}"})
    int executeUpdate(Map map);


    @Update({" <script> ${head}" +
            " <foreach collection ='list' item='item' separator =','>  " +
            " ${body}" +
            " </foreach>" +
            " </script>"})
    int executeUpdateBatch(Map map);


}
