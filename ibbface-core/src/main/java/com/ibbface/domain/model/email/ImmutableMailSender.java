package com.ibbface.domain.model.email;

import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A immutable {@link MailSender} implementation.
 *
 * @author Fuchun
 * @since 1.0
 */
public final class ImmutableMailSender implements MailSender {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final String host;
    private final int port;
    private final String name;
    private final String account;
    private final String password;
    private final boolean needAuthenticate;

    public ImmutableMailSender(String host, int port, String name, String account,
                               String password, boolean needAuthenticate) {
        this.host = host;
        this.port = port;
        this.name = name;
        this.account = account;
        this.password = password;
        this.needAuthenticate = needAuthenticate;
    }

    public void initialize() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Initialize mail sender client information: {}",
                    String.format("host=%s,port=%s,name=%s,account=%s,password=%s,authenticate=%s",
                            getHost(), getPort(), getName(), getAccount(),
                            getMaskedPassword(), isNeedAuthenticate()));
        }
    }

    String getMaskedPassword() {
        return Joiner.on("").join(password.charAt(0), "*", password.charAt(password.length() - 1));
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAccount() {
        return account;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isNeedAuthenticate() {
        return needAuthenticate;
    }

    @Override
    public String toString() {
        return "ImmutableMailSender{" +
                "host='" + getHost() + '\'' +
                ", port=" + getPort() +
                ", name='" + getName() + '\'' +
                ", account='" + getAccount() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", needAuthenticate=" + isNeedAuthenticate() +
                '}';
    }
}
