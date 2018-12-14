package com.teamrandom.softproj.service.mail;

import org.springframework.mail.SimpleMailMessage;

/**
 * Created by Olga on 8/22/2016.
 */

public interface EmailService {
    void sendSimpleMessage(String to,
                           String subject,
                           String text);
    void sendSimpleMessageUsingTemplate(String to,
                                        String subject,
                                        SimpleMailMessage template,
                                        String ...templateArgs);
}