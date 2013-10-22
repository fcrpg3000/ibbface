package com.ibbface.service;

import com.ibbface.domain.model.email.EmailEntry;

/**
 * The e-mail service.
 *
 * @author Fuchun
 * @version $Id$
 * @since 1.0
 */
public interface EmailService {

    /**
     * Synchronous send mail of the specified email entry information.
     *
     * @param emailEntry e-mail entry.
     * @throws NullPointerException if {@code emailEntry} is null.
     */
    public void sendMail(EmailEntry emailEntry);

    /**
     * Asynchronous send mail of the specified email entry information.
     *
     * @param emailEntry e-mail entry.
     * @throws NullPointerException if {@code emailEntry} is null.
     */
    public void asyncSendMail(EmailEntry emailEntry);
}
