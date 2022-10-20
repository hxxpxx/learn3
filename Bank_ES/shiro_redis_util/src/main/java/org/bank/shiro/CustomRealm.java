package org.bank.shiro;


import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.bank.constants.Constant;
import org.bank.utils.JwtTokenUtil;

import java.util.Collection;

/**
 * @BelongsProject: BankCloud
 * @BelongsPackage: org.bank.config
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
@Slf4j
public class CustomRealm extends AuthorizingRealm {


    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CustomPasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String accessToken = (String) SecurityUtils.getSubject().getPrincipal();
        String userId = JwtTokenUtil.getUserId(accessToken);
        log.info("userId={}", userId);
        Claims claimsFromToken = JwtTokenUtil.getClaimsFromToken(accessToken);
        if (claimsFromToken.get(Constant.JWT_ROLES_KEY) != null) {
            authorizationInfo.addRoles((Collection<String>) claimsFromToken.get(Constant.JWT_ROLES_KEY));
        }
        if (claimsFromToken.get(Constant.JWT_PERMISSIONS_KEY) != null) {
            authorizationInfo.addStringPermissions((Collection<String>) claimsFromToken.get(Constant.JWT_PERMISSIONS_KEY));
        }
        log.info("authorizationInfo.getStringPermissions().toString()===========");
        log.info(authorizationInfo.getStringPermissions().toString());
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        CustomPasswordToken token = (CustomPasswordToken) authenticationToken;
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(token.getPrincipal(), token.getPrincipal(), getName());
        return simpleAuthenticationInfo;
    }
}
