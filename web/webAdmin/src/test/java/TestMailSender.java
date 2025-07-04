//package com.heds.project;
//
//import com.heds.project.utils.MailService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class TestMailSender{
//
//    @Autowired
//    private MailService mailService;
//    @Test
//    //Email test
//    public void testEmail(){
//        // 模拟发送邮件
//        String to = "kaku616263@gmail.com"; // 收件人
//        String subject = "测试邮件";
//        String text = "这是一封来自 Spring Boot 的测试邮件";
//
//        mailService.sendSimpleMail(to, subject, text);
//        System.out.println("邮件已发送！");
//    }
//}
