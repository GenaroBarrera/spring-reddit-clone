package com.example.springredditclone.service;

import com.example.springredditclone.exceptions.SpringRedditException;
import com.example.springredditclone.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Class: Mail Service
 * Let us understand what we are doing in this class,
 * so we have our sendMail method which takes NotificationEmail as input,
 * and inside the method we are creating a MimeMessage by passing in the sender, recipient, subject and body fields.
 * The message body we are receiving from the build() method of our MailContentBuilder class.
 */

/**
 * We mark beans with @Service to indicate that it's holding the business logic.
 * So there's not any other specialty except using it in the service layer.
 */
@Service //@Service annotates classes at the service layer
@AllArgsConstructor
/**
 * @Slf4j will generate
 * public class LogExample {
 *      private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogExample.class);
 *  }
 */
@Slf4j //lombok Causes lombok to generate a logger field.
class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    /**
     * @Async
     * Also, add the @Async to our sendMail method inside the MailService class.
     * You can compare that the previous request (in Postman) took around 11132 ms
     * but after activating Async we got back the response in just 495 ms.
     * @param notificationEmail
     */
    @Async
    void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springreddit@email.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            //messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
            messageHelper.setText(notificationEmail.getBody());
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        } catch (MailException e) {
            //
            log.error("Exception occurred when sending mail", e);
            throw new SpringRedditException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
        }
    }
}
