package cn.exsolo.console;

import cn.exsolo.batis.act.mapper.DdlWorkMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author prestolive
 * @date 2023/3/8
 **/

@SpringBootTest
public class OrmTest {

    @Autowired
    private DdlWorkMapper ddlWorkMapper;

    @Test
    public void testDdl(){
//        List<ActTableColumnBO> list = ddlWorkMapper.selectTableColumnAll();
//        System.out.println(list.size());
    }
}
