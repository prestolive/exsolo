package cn.exsolo.authserver.shiro;

import cn.exsolo.auth.shiro.LoginAuthenticationToken;
import cn.exsolo.authserver.service.AuthService;
import cn.exsolo.authserver.vo.UserAuthVO;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author prestolive
 * @date 2021/5/25
 **/
public class LoginRealm extends AuthorizingRealm {

    @Autowired
    private AuthService authService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof LoginAuthenticationToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        LoginAuthenticationToken token = (LoginAuthenticationToken) authenticationToken;
        String loginCode = (String) token.getPrincipal();
        UserAuthVO  userAuthVO = authService.getUserAuthByLoginCode(loginCode);
        if(userAuthVO==null){
            throw new UnknownAccountException();
        }
        if(!"NORMAL".equals(userAuthVO.getStatus())){
            throw new AccountException();
        }
        LoginAuthenticationInfo loginAuthenticationInfo = new LoginAuthenticationInfo(userAuthVO.getLoginCode(),userAuthVO.getEncrypt(),userAuthVO.getSalt());
        return loginAuthenticationInfo;
    }
}
