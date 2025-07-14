package com.heds.project.controller;

import com.heds.project.Service.ConsultationServer;
import com.heds.project.config.modul.Consultation;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.heds.project.utils.MailService;
import com.heds.project.mapper.ConsultationMapper;
@RestController
@RequestMapping("/user")
public class UserEmailController {
    @Autowired
    private MailService mailService;
    @Autowired
    private ConsultationMapper consultationMapper;
    @PostMapping("/submit")
    public void submitInquiry(@RequestBody Consultation consultation) {
        System.out.println("接收到的数据：" + consultation);
        String to = "kaku616263@gmail.com"; // 收件人
        String subject = "咨询邮件";
        String htmlContent = """
            <div style="font-family: Arial, sans-serif; line-height: 1.6;">
              <h2>用户咨询信息</h2>
              <table style="border-collapse: collapse; width: 100%%;">
                <tr><th align="left">姓名：</th><td>%s</td></tr>
                <tr><th align="left">邮箱：</th><td>%s</td></tr>
                <tr><th align="left">电话：</th><td>%s</td></tr>
                <tr><th align="left">留言内容：</th><td>%s</td></tr>
              </table>
            </div>
        """.formatted(
                consultation.getName(),
                consultation.getEmail(),
                consultation.getPhone(),
                consultation.getMessage()
            );
        try {
            mailService.sendHtmlMail(to, subject, htmlContent);
            System.out.println("发送成功 Successful");
            //存入数据库
            //store to database
           int secIns = consultationMapper.insert(consultation);
           if (secIns != 1) {
               System.out.println("<UNK> 插入错误 insertError");
           }
        } catch (MessagingException e) {
            System.out.println("发送失败 Failed");
        } catch (Exception e) {
            System.out.println("插入过程中出现异常：imsert Errpr!" + e.getMessage());

        }
    }
}
