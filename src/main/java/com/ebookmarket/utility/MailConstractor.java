package com.ebookmarket.utility;

import com.ebookmarket.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MailConstractor {

    @Autowired
    private Environment env;

    public SimpleMailMessage constractRestTokenEmail(
            String contextPath, Locale locale, String token, User user, String password
        ) {

        String url = contextPath = "/newUser?token=" + token;
        String message = "\n Please click on this link to verify your email and edit your personal information. Your password is : \n" + password;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Ebook Market - New User");
        email.setText(url + message);
        email.setFrom(env.getProperty("support.email"));

        return email;
    }
}
