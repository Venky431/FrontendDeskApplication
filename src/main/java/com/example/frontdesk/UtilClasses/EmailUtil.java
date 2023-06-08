package com.example.frontdesk.UtilClasses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
@Component
public class EmailUtil {
    @Autowired
    private JavaMailSender javaMail;
    public boolean sendMessage(String to, String subject, String body){
        try {
            MimeMessage mimeMessage = javaMail.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body,true);
            javaMail.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return false;
    }
}
