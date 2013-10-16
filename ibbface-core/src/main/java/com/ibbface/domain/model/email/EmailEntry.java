package com.ibbface.domain.model.email;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Fuchun
 * @version $Id$
 * @since 1.0
 */
public interface EmailEntry extends Serializable {

    /**
     * This e-mail subject.
     */
    public String getSubject();

    /**
     * This e-mail content.
     */
    public String getContent();

    /**
     * Returns the name of sender.
     */
    public MailSender getSender();

    /**
     * Add a recipient to contain email and name.
     *
     * @param email recipient's e-mail.
     * @param name recipient's name.
     * @return this email entry.
     */
    public EmailEntry addRecipient(String email, String name);

    /**
     * Returns the recipients map of the email and name.
     * The result map key is e-mail, and value is name of recipient.
     */
    public Map<String, String> allRecipients();
}
