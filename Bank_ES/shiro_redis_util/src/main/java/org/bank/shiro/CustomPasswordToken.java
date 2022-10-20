package org.bank.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @BelongsProject: BankCloud
 * @BelongsPackage: org.bank.shiro
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
public class CustomPasswordToken  extends UsernamePasswordToken {
    private String token;

    public CustomPasswordToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }
}
