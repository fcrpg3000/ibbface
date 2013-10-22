package com.ibbface.domain.model.email;

/**
 * The mail sender information.
 *
 * @author Fuchun
 * @since 1.0
 */
public interface MailSender {

    /**
     * Returns the e-mail service host domain. e.g. smtp.163.com
     */
    public String getHost();

    /**
     * Returns the e-mail service port. default: 25.
     */
    public int getPort();

    /**
     * Returns the e-mail sender name.
     */
    public String getName();

    /**
     * Returns the e-mail account.
     */
    public String getAccount();

    /**
     * Returns the e-mail account password.
     */
    public String getPassword();

    /**
     * Returns {@code true} if this sender need authenticate account, otherwise {@code false}.
     */
    public boolean isNeedAuthenticate();
}
