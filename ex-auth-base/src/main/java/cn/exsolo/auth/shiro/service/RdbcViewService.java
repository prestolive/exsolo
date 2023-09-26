package cn.exsolo.auth.shiro.service;

import cn.exsolo.batis.core.BaseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author prestolive
 * @date 2021/7/11
 **/
@Service
public class RdbcViewService {

    @Autowired
    private BaseDAO baseDAO;

    public List<String> getPermission(String userCode){
        return null;
    }
}
