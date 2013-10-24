package com.ibbface.interfaces.realm;

import com.google.common.base.Objects;
import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 * @author Fuchun
 * @since 1.0
 */
public class AccountPasswordToken implements HostAuthenticationToken, RememberMeAuthenticationToken {

    private String account;
    private String password;
    private String host;
    private boolean rememberMe;

    public AccountPasswordToken(String account, String password) {
        this(account, password, false, null);
    }

    public AccountPasswordToken(String account, String password, boolean rememberMe) {
        this(account, password, rememberMe, null);
    }

    public AccountPasswordToken(String account, String password, String host) {
        this(account, password, false, host);
    }

    public AccountPasswordToken(String account, String password, boolean rememberMe, String host) {
        this.account = account;
        this.password = password;
        this.rememberMe = rememberMe;
        this.host = host;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public void clear() {
        account = null;
        password = null;
        host = null;
        rememberMe = false;
    }

    @Override
    public Object getPrincipal() {
        return getAccount();
    }

    @Override
    public Object getCredentials() {
        return getPassword();
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }

    protected Objects.ToStringHelper toStringHelper() {
        return Objects.toStringHelper(getClass())
                .add("account", getAccount())
                .add("password", getPassword())
                .add("host", getHost())
                .add("rememberMe", isRememberMe());
    }
}
