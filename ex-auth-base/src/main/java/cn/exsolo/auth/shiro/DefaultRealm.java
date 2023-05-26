package cn.exsolo.auth.shiro;

import cn.exsolo.auth.utils.TokenUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author prestolive
 * @date 2023/5/24
 **/
public class DefaultRealm extends AuthorizingRealm {

    @Value("${exsolo.auth.publicKey}")
    private String authPublicKey;

    /**
     * 提供用户信息返回权限信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
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
        String realName = null;
        if (authenticationToken instanceof DefaultAuthenticationToken) {
            DefaultAuthenticationToken defaultToken = (DefaultAuthenticationToken) authenticationToken;
            String token = defaultToken.getToken();
            TokenUtil.verify(token, authPublicKey);
            return new SimpleAuthenticationInfo(authenticationToken.getPrincipal(), authenticationToken.getCredentials(), realName);
        }
        throw new AuthenticationException("未定义的认证方式");
//        String token = (String) authenticationToken.getPrincipal()


    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof DefaultAuthenticationToken;
    }
}
