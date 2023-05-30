package cn.exsolo.auth.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.realm.Realm;

/**
 * to fix ,in multi realms mode when throws exception the shiro throw AuthenticationException
 * Authentication token of type * could not be authenticated by any configured realms.
 * Please ensure that at least one realm can authenticate these tokens.
 *
 * @author prestolive
 */
public class FixShiroAtLeastOneSuccessfulStrategy extends AtLeastOneSuccessfulStrategy {

    @Override
    public AuthenticationInfo afterAttempt(Realm realm, AuthenticationToken token,
                                           AuthenticationInfo info,
                                           AuthenticationInfo authenticationInfo,
                                           Throwable e) throws AuthenticationException {
        if (e instanceof AuthenticationException) {
            throw (AuthenticationException) e;
        }
        return super.afterAttempt(realm, token, info, authenticationInfo, e);
    }


}