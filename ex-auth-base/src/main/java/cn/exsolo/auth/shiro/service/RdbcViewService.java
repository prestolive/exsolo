package cn.exsolo.auth.shiro.service;

import cn.exsolo.batis.core.BaseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author prestolive
 * @date 2021/7/11
 **/
@Service
public class RdbcViewService {

    @Autowired
    private BaseDAO baseDAO;

    public List<String> getPermission(String user){
        String sql ="select distinct a.permission from ex_user_flat_permission a where a.userid=#{userId}";
        Map<String,Object> param = new HashMap<>();
        param.put("userId",user);
        List<String> list = baseDAO.queryForList(sql,param,String.class);
        return list;
    }
}
