package com.ibbface.service.internal;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.ibbface.domain.model.email.EmailEntry;
import com.ibbface.service.EmailService;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Fuchun
 * @since 1.0
 */
@Service("emailService")
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class.getName());

    private final SenderThreadService senderThreadService;

    public EmailServiceImpl() {
        this.senderThreadService = new SenderThreadService(this);
    }

    @PostConstruct
    public void init() {
        senderThreadService.startAsync().awaitRunning();
    }

    @PreDestroy
    public void destroy() {
        if (senderThreadService.isRunning()) {
            senderThreadService.stopAsync();
            senderThreadService.awaitTerminated();
        }
    }

    @Override
    public void sendMail(EmailEntry emailEntry) {
        checkEmailEntry(emailEntry);
        sendHtmlEmail(emailEntry);
    }

    @Override
    public void asyncSendMail(EmailEntry emailEntry) {
        checkEmailEntry(emailEntry);
        if (senderThreadService == null || !senderThreadService.isRunning()) {
            LOGGER.error("The email sender thread service initialize failed.");
        } else {
            senderThreadService.addEmailEntry(emailEntry);
        }
    }

    void sendHtmlEmail(final EmailEntry emailEntry) {
        HtmlEmail htmlEmail = new HtmlEmail();
        htmlEmail.setHostName(emailEntry.getSender().getHost());
        htmlEmail.setSmtpPort(emailEntry.getSender().getPort());
        htmlEmail.setAuthentication(emailEntry.getSender().getAccount(),
                emailEntry.getSender().getPassword());
        htmlEmail.setCharset(Charsets.UTF_8.displayName());
        htmlEmail.setSubject(emailEntry.getSubject());

        try {
            htmlEmail.setFrom(emailEntry.getSender().getAccount(), emailEntry.getSender().getName());
            htmlEmail.setHtmlMsg(emailEntry.getContent());

            for (Map.Entry<String, String> entry : emailEntry.allRecipients().entrySet()) {
                htmlEmail.addTo(entry.getKey(), entry.getValue());
            }
            htmlEmail.send();
        } catch (EmailException ex) {
            // Write EmailEntry information and reason for not be sent.
            LOGGER.error("HtmlEmail `from` or `htmlMsg` is error. Cause: ", ex);
        }
    }

    private void checkEmailEntry(final EmailEntry emailEntry) {
        checkNotNull(emailEntry, "The given `emailEntry` must be not null.");
    }

    private static class SenderThreadService extends AbstractExecutionThreadService {

        final EmailServiceImpl emailService;
        final List<EmailEntry> emailList = Lists.newArrayList();

        private SenderThreadService(EmailServiceImpl emailService) {
            this.emailService = emailService;
        }

        public void addEmailEntry(final EmailEntry emailEntry) {
            synchronized (emailList) {
                emailList.add(emailEntry);
                emailList.notifyAll();
            }
        }

        @Override
        protected String serviceName() {
            return "EmailSenderService";
        }

        @Override
        protected void run() throws Exception {
            while (isRunning()) {
                if (emailList.isEmpty()) {
                    try {
                        synchronized (emailList) {
                            if (LOGGER.isDebugEnabled()) {
                                LOGGER.debug("The `emailList`(List<EmailEntry>) is empty, wait new email entry.");
                            }
                            emailList.wait();
                        }
                    } catch (InterruptedException ex) {
                        // ignore
                    }
                }
                List<EmailEntry> emailEntries;
                synchronized (emailList) {
                    emailEntries = Lists.newArrayList(emailList);
                    emailList.clear();
                }
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Waiting email list contains some of entry to be sent.");
                }
                for (EmailEntry emailEntry : emailEntries) {
                    emailService.sendHtmlEmail(emailEntry);
                }
            }
        }
    }
//
//    public static void main(String[] args) {
//        final EmailServiceImpl emailService = new EmailServiceImpl();
//        emailService.init();
//
//        System.out.println(emailService.senderThreadService.isRunning());
//        final MailSender sender = new ImmutableMailSender(
//                "smtp.ym.163.com", 25, "爱童颜", "service@ibbface.org", "9trhSnINcq", true);
//
//
//        int count = 1;
//        while (count != 2) {
//            emailService.asyncSendMail(EmailEntries.newEmailEntry("测试邮件" + count,
//                    "这个是测试邮件！请误回复，谢谢！", sender)
//                .addRecipient("fcrpg2008@163.com", "付春"));
//            count++;
//        }
//        emailService.destroy();
//    }
}
