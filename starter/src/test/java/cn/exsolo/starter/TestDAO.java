package cn.exsolo.starter;

import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.batis.core.Condition;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

/**
 * 文件描述
 *
 * @author prestolive
 * @version 1.0
 * @date 2025/4/8 10:05
 * Copyright © 2025 厦门象屿股份有限公司. All Rights Reserved
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ExSoloApplication.class})
public class TestDAO {


    @Autowired
    private BaseDAO baseDAO;


    @Test
    public void testBaseDAO(){
        String[] sqls=  new String[]{
                "select logincode from ex_user",
                "select logincode from ex_user where 1=1",
                "select logincode from ex_user where 1=1 order by id",
                "select logincode from ex_user order by id"
        };
        List<Condition> conds = new ArrayList<>();
        conds.add(new Condition());
        List<String> xx = new ArrayList<>();
        xx.add("ddd");
        conds.add(new Condition().in("id",xx));
        conds.add(new Condition().ge("id","0").orderBy("createTs"));
        conds.add(new Condition().orderBy("createTs"));

            for(String sql:sqls){
                for(int i=0;i<conds.size();i++){
                    Condition cond = conds.get(i);
                    try{
                        Map<String,Object> values= new HashMap<>();
                        List<String> result = baseDAO.queryForList(sql,cond,values,String.class);
                        System.out.println(StringUtils.join(result,","));
                    }catch (Exception e){
                        e.printStackTrace();
                        System.out.printf("%s,错误cond:%d%n",sql,i);
                        throw e;
                    }
                }
            }
    }




}
