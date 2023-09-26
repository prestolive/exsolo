package cn.exsolo.authserver.service;

import cn.exsolo.authserver.vo.UserAuthVO;
import cn.exsolo.authserver.vo.UserVO;
import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.kit.utils.ExAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author prestolive
 * @date 2021/5/26
 **/
@Service
public class AuthService {

    @Autowired
    private BaseDAO baseDAO;

    public UserAuthVO getUserAuthByLoginCode(String loginCode){
        ExAssert.isNull(loginCode);
        String sql = " select a.id userId,a.loginCode,a.userName,a.status,b.encrypt,b.salt" +
                " from ex_user a" +
                " left join ex_user_encrypt b on a.id= b.userid and b.active=true" +
                " where a.loginCode = #{loginCode}";
        Map<String,Object> params = new HashMap<>();
        params.put("loginCode",loginCode);
        List<UserAuthVO> list = baseDAO.queryForList(sql,params,UserAuthVO.class);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    public UserVO getUserByLoginCode(String loginCode){
        ExAssert.isNull(loginCode);
        String sql = " select a.id userId,a.loginCode,a.userName,a.status" +
                " from ex_user a"+
                " where a.loginCode = #{loginCode}";
        Map<String,Object> params = new HashMap<>();
        params.put("loginCode",loginCode);
        List<UserVO> list = baseDAO.queryForList(sql,params,UserVO.class);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
