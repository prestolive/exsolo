package cn.exsolo.authserver.service;

import cn.exsolo.authserver.po.AliveInstancePO;
import cn.exsolo.authserver.vo.UserAuthVO;
import cn.exsolo.authserver.vo.UserVO;
import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.kit.utils.ExAssert;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

    public UserAuthVO getUserAuthByLoginCode(String loginCode) {
        ExAssert.isNull(loginCode);
        String sql = " select a.id userId,a.loginCode,a.userName,a.status,b.encrypt,b.salt" +
                " from ex_user a" +
                " left join ex_user_encrypt b on a.id= b.userid and b.active=true" +
                " where a.loginCode = #{loginCode}";
        Map<String, Object> params = new HashMap<>();
        params.put("loginCode", loginCode);
        List<UserAuthVO> list = baseDAO.queryForList(sql, params, UserAuthVO.class);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public UserVO getUserByLoginCode(String loginCode) {
        ExAssert.isNull(loginCode);
        String sql = " select a.id userId,a.loginCode,a.userName,a.status" +
                " from ex_user a" +
                " where a.loginCode = #{loginCode}";
        Map<String, Object> params = new HashMap<>();
        params.put("loginCode", loginCode);
        List<UserVO> list = baseDAO.queryForList(sql, params, UserVO.class);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 将refresh token信息保存到数据库中
     * @param refreshToken
     * @param refreshTokenExpireTimes
     */
    public void saveAliveInstance(String refreshToken, int refreshTokenExpireTimes) {
        ExAssert.isNull(refreshToken);
        AliveInstancePO instancePO = new AliveInstancePO();
        DecodedJWT jwt = JWT.decode(refreshToken);
        instancePO.setId(getTokenProp(jwt,"ticket"));
        instancePO.setLoginCode(getTokenProp(jwt,"code"));
        instancePO.setIp(getTokenProp(jwt,"ip"));
        instancePO.setUa(getTokenProp(jwt,"ua"));
        instancePO.setPlatform(getTokenProp(jwt,"platform"));
        instancePO.setStatus(0);
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String activeTs = time.format(dateTimeFormatter);
        time.plusSeconds(refreshTokenExpireTimes);
        String expireTs = time.format(dateTimeFormatter);
        instancePO.setActiveTs(activeTs);
        instancePO.setExpireTs(expireTs);
        baseDAO.insertOrUpdateValueObject(instancePO);
    }

    private String getTokenProp(DecodedJWT jwt, String field) {
        Claim claim = jwt.getClaim(field);
        if(claim!=null){
            return claim.asString();
        }
        return "";
    }
}
