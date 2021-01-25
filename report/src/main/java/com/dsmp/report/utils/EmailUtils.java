package com.dsmp.report.utils;

import com.dsmp.report.common.bo.EmailBo;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * @author byliu
 **/
public class EmailUtils {

    public static boolean sendEmail(EmailBo emailBo, String to, String subject, String content) {
        JavaMailSenderImpl sender = getMailSender(emailBo.getEmailServer(), emailBo.getEmailPort(),
                emailBo.getEmailUser(), emailBo.getEmailPassword());
        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailBo.getEmailUser());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            sender.send(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * @Description:
     * @Author: byliu
     * @Date: 2021/1/8 11:07
     * @param emailBo: 
     * @param toEmail: 收件人集合
     * @param subjectText: 主题
     * @param messageText: 内容
     * @param inputStreamMaps: 附件输入流
     * @return: boolean
     **/
    public static boolean sendMessage(EmailBo emailBo, String[] toEmail, String subjectText, String messageText, Map<String, InputStream> inputStreamMaps){
        return sendMessage(emailBo.getEmailServer(), emailBo.getEmailPort(), emailBo.getEmailUser(), emailBo.getEmailPassword(),
                toEmail, subjectText, messageText, inputStreamMaps);
    }

    /**
     * @param emailServer:     邮件服务器
     * @param emailPort:       端口
     * @param emailUser:       用户名
     * @param emailPassword:   密码
     * @param toEmail:         收件人集合
     * @param subjectText:     主题
     * @param messageText:     内容
     * @param inputStreamMaps: 附件输入流
     **/
    public static boolean sendMessage(String emailServer, Integer emailPort, String emailUser, String emailPassword, String[] toEmail, String subjectText, String messageText, Map<String, InputStream> inputStreamMaps) {
        System.setProperty("mail.mime.splitlongparameters","false");
        JavaMailSenderImpl sender = getMailSender(emailServer, emailPort,
                emailUser, emailPassword);
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailUser);
            helper.setTo(toEmail);
            helper.setSubject(subjectText);
            helper.setText(messageText, true);
            Iterator<String> iterator = inputStreamMaps.keySet().iterator();
            while (iterator.hasNext()) {
                String k = iterator.next();
                helper.addAttachment(k,  new ByteArrayResource(IOUtils.toByteArray(inputStreamMaps.get(k))));
            }
        } catch (MessagingException | IOException e) {
            return false;
        }
        try {
            sender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    private static JavaMailSenderImpl getMailSender(String emailServer, Integer emailPort, String emailUser, String emailPassword) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(emailServer);
        sender.setUsername(emailUser);
        sender.setPassword(emailPassword);
        sender.setPort(emailPort);
        sender.setDefaultEncoding("UTF-8");

        return sender;
    }

    public static void main(String[] args) {
        /*InputStream dsmp = MinIoComponent.getObject("dsmp", "2021-01-07/d569213714d042eba574ebe287550790.pdf");
        Map<String, InputStream> map = new HashMap();
        map.put("test.pdf", dsmp);
        sendMessage("smtp.qq.com",465,"825869330@qq.com","jidsybmkquhubfej",new String[]{"1272501502@qq.com"},"ssssssss","ssssssssssss",map);*/
    }
}
