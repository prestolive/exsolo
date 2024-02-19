package cn.exsolo.auth.shiro;

import cn.exsolo.auth.shiro.service.RdbcViewService;
import cn.exsolo.auth.utils.AccessTokenUtil;
import cn.exsolo.kit.cache.CacheEnum;
import cn.exsolo.kit.cache.IExCache;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author prestolive
 * @date 2021/5/24
 **/
@Component("defaultRealm")
public class DefaultRealm extends AuthorizingRealm {

    @Value("${exsolo.auth.publicKey}")
    private String authPublicKey;

    @Autowired
    private IExCache iExCache;

    @Autowired
    private RdbcViewService rdbcViewService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof DefaultAuthenticationToken;
    }

    /**
     * 提供用户信息返回权限信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //FIXME 现在不走这个方法了 查下为啥
        Set<String> realNames =  principalCollection.getRealmNames();
        String realName = null;
        if(realNames.size()>0){
            realName = realNames.stream().findFirst().get();
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> permissions = new HashSet<>();
        if(StringUtils.isNotEmpty(realName)){
            String[] caches = iExCache.getCache(CacheEnum.PERMISSION).getStringArray(realName);
            if(caches==null){
                List<String> list = rdbcViewService.getPermission(realName);
                if(list!=null){
                    caches = list.toArray(new String[list.size()]);
                }
                iExCache.getCache(CacheEnum.PERMISSION).putStringArray(realName,caches);
            }
            if(caches!=null&&caches.length>0){
                permissions.addAll(Arrays.stream(caches).collect(Collectors.toList()));
            }
        }
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    /**
     * 提供账户信息返回用户用于验证的信息
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        if (authenticationToken instanceof DefaultAuthenticationToken) {
            DefaultAuthenticationToken defaultToken = (DefaultAuthenticationToken) authenticationToken;
            String token = defaultToken.getToken();
            AccessTokenUtil.verifyAccessToken(token, authPublicKey);
            String userCode = AccessTokenUtil.getUserCode(token);
            String userId = AccessTokenUtil.getUserID(token);
            return new SimpleAuthenticationInfo("0", "0", userId);
        }
        throw new AuthenticationException("未定义的认证方式");
//        String token = (String) authenticationToken.getPrincipal()


    }

}
