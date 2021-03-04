package com.example.springredditclone.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Class: MailContentBuilder Service
 * MailContentBuilder.java contains the method build()
 * which takes our email message as input and it uses the Thymeleaf‘s TemplateEngine to generate the email message.
 * Note that we gave the name mailTemplate as an argument to the method call templateEngine.process(“mailTemplate”, context);
 * That would be the name of the html template
 */
@Service
@AllArgsConstructor
class MailContentBuilder {

    private final TemplateEngine templateEngine;

    String build(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("mailTemplate", context);
    }
}
