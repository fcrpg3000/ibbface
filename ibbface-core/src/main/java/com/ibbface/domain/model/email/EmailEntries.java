package com.ibbface.domain.model.email;

import com.google.common.base.Supplier;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author Fuchun
 * @version $Id$
 * @since 1.0
 */
public final class EmailEntries {

    /**
     * Gets a {@link EmailEntry} with the specified email subject, sender and content supplier.
     *
     * @param subject email subject.
     * @param sender email sender information.
     * @param contentSupplier content supplier (not null).
     * @return A new EmailEntry.
     * @throws IllegalArgumentException if {@code contentSupplier == null}.
     */
    public static EmailEntry newEmailEntry(
            String subject, MailSender sender, Supplier<String> contentSupplier) {
        if (contentSupplier == null) {
            throw new IllegalArgumentException("The given `contentSupplier` must be not null.");
        }
        return newEmailEntry(subject, contentSupplier.get(), sender);
    }

    /**
     * Gets a {@link EmailEntry} with the specified email subject, content and sender.
     * The {@link EmailEntry} returned have not specified recipient.
     *
     * @param subject email subject.
     * @param content email content.
     * @param sender email sender information.
     * @return a new EmailEntry.
     */
    public static EmailEntry newEmailEntry(String subject, String content, MailSender sender) {
        return newEmailEntry(subject, content, sender, null);
    }

    /**
     * Gets a {@link EmailEntry} with the specified email subject, content, sender and recipients.
     *
     * @param subject email subject.
     * @param content email content.
     * @param sender email sender information.
     * @param recipients email recipients (key =&gt; email, value =&gt; name).
     * @return a new EmailEntry.
     */
    public static EmailEntry newEmailEntry(
            String subject, String content, MailSender sender, Map<String, String> recipients) {
        DefaultEmailEntry emailEntry = new DefaultEmailEntry(subject, content, sender);
        if (recipients != null && !recipients.isEmpty()) {
            emailEntry.addRecipients(recipients);
        }
        return emailEntry;
    }

    private static class DefaultEmailEntry implements EmailEntry {
        private static final long serialVersionUID = 1L;

        private final Map<String, String> recipientMap = Maps.newHashMap();
        private final String subject;
        private final String content;
        private final MailSender sender;

        public DefaultEmailEntry(String subject, String content, MailSender sender) {
            this.subject = subject;
            this.content = content;
            this.sender = sender;
        }

        public void addRecipients(Map<String, String> recipients) {
            recipientMap.putAll(recipients);
        }

        @Override
        public DefaultEmailEntry addRecipient(String email, String name) {
            recipientMap.put(email, name);
            return this;
        }

        @Override
        public String getSubject() {
            return subject;
        }

        @Override
        public String getContent() {
            return content;
        }

        @Override
        public MailSender getSender() {
            return sender;
        }

        @Override
        public Map<String, String> allRecipients() {
            return recipientMap;
        }
    }
}
