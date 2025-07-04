package com.heds.project.controller;

import com.heds.project.config.modul.Consultation;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.heds.project.utils.MailService;
@RestController
@RequestMapping("/user")
public class UserEmailController {
    @Autowired
    private MailService mailService;
    @PostMapping("/submit")
    public void submitInquiry(@RequestBody Consultation consultation) {
        System.out.println("接收到的数据：" + consultation);
        String to = "kaku616263@gmail.com"; // 收件人
        String subject = "测试邮件";
        String htmlContent = """
            <div style="font-family: Arial, sans-serif; line-height: 1.6;">
              <h2>用户咨询信息</h2>
              <table style="border-collapse: collapse; width: 100%%;">
                <tr><th align="left">ID：</th><td>%d</td></tr>
                <tr><th align="left">姓名：</th><td>%s</td></tr>
                <tr><th align="left">邮箱：</th><td>%s</td></tr>
                <tr><th align="left">电话：</th><td>%s</td></tr>
                <tr><th align="left">留言内容：</th><td>%s</td></tr>
                <tr><th align="left">状态：</th><td>%s</td></tr>
              </table>
            </div>
        """.formatted(
                consultation.getId(),
                consultation.getName(),
                consultation.getEmail(),
                consultation.getPhone(),
                consultation.getMessage(),
                consultation.getStatus()
            );
        try {
            mailService.sendHtmlMail(to, subject, htmlContent);
            System.out.println("发送成功");
        } catch (MessagingException e) {
            System.out.println("发送失败");
        }
    }
}
