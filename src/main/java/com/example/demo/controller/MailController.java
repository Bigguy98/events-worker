package com.example.demo.controller;

import com.example.demo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @GetMapping("/send")
    public void sendMail() {
        mailService.sendNewMail("thanhtungaquarius@gmail.com", "TEST SENDING MAIL", "hI tehre \n This is a bot message");
    }

    @GetMapping("/sendWithAttachment")
    public void sendWithAttachment() {
        mailService.sendMailWithAttachment("thanhtungaquarius@gmail.com", "TEST SENDING MAIL", "hI tehre \n This is a bot message","compressed.zip");
    }
}
