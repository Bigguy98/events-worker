package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSenderImpl mailSender;

    public void sendNewMail(String to, String subject, String body)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendMailWithAttachment(String to, String subject, String body, String fileToAttach)
    {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        FileSystemResource file = new FileSystemResource(fileToAttach);
        try {
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("solana.bootcamp@gmail.com");
            helper.setSubject(subject);
            helper.setText(body);
            helper.addAttachment(file.getFilename(), file);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        try {
            mailSender.send(mimeMessage);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }


}
