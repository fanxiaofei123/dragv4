package org.com.drag.common.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by cdyoue on 2016/9/20.
 */
public class JavaMailUtils {
    /**
     * 1.创建Session对象
     */
    public static Session createSession(){

        Properties prop = new Properties();
        prop.setProperty("mail.smtp.host",MailAuthorSetting.USERHOST);	//指定主机名
        prop.setProperty("mail.smtp.auth", MailAuthorSetting.AUTH);	//使用安全验证
        prop.setProperty("mail.smtp.starttls.enable",MailAuthorSetting.ENABLE);	//使用 STARTTLS安全连接

        //创建验证器
        Authenticator auth = new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(MailAuthorSetting.USERNAME,MailAuthorSetting.PASSWORD);	//此处要求username和password是final类型
            }
        };

        return Session.getInstance(prop,auth);
    }

    /**
     * 2.编写邮件
     * @throws MessagingException
     */
    public static MimeMessage writeRetrieveEmail(String url,String token,Session session, String from, String to, String subject) throws MessagingException {
        StringBuffer sb = new StringBuffer();
        sb.append("亲爱的用户 ");
        sb.append(to);
        sb.append("：您好！<br><br>");
        sb.append("        您收到这封这封电子邮件是因为您 (也可能是某人冒充您的名义) 申请了一个新的密码。假如这不是您本人所申请, 请不用理会<br>这封电子邮件, 但是如果您持续收到这类的信件骚扰, 请您尽快联络管理员。<br><br>");
        sb.append("        要使用新的密码, 请使用以下链接启用密码。<br><br>");
        sb.append("        <a href='");
        sb.append(url);
        sb.append("?token=");
        sb.append(token);
        sb.append("&email=");
        sb.append(to);
        sb.append("'>");
        sb.append(url);
        sb.append("?token=");
        sb.append(token);
        sb.append("&email=");
        sb.append(to);
        sb.append("</a>");




        MimeMessage message = getMessage(session, from, to, subject);


        message.setContent(sb.toString(),"text/html;charset=utf-8");

        return message;
    }


    /**
     * 2.编写邮件
     * @throws MessagingException
     */
    public static MimeMessage writeEmail(String url,String token,Session session, String from, String to, String subject) throws MessagingException {
        String content  = "<p>您好:"+to+"<br><br>欢迎加入我们!<br><br>帐户需要激活才能使用，赶紧激活成为正式的一员吧:)<br><br>请在24小时内点击下面的链接立即激活帐户："
                +"<br><a href='"+url+"?token="+token+"&email="+to+"'>"
                +url+"?token="+token+"&email="+to+"</a></p>";

        MimeMessage message = getMessage( session, from, to, subject);
        message.setContent(content,"text/html;charset=utf-8");
        return message;
    }



    protected static MimeMessage getMessage(Session session, String from, String to, String subject) throws MessagingException {
        //发件人地址
        InternetAddress fromAddress = new InternetAddress(from);
        //收件人地址
        InternetAddress toAddress = new InternetAddress(to);
        //创建MimeMessage对象
        MimeMessage message = new MimeMessage(session);
        //设置发件人
        message.setFrom(fromAddress);
        //设置收件人
        message.addRecipient(MimeMessage.RecipientType.TO, toAddress);
        //设置主题
        message.setSubject(subject);
        return message;
    }

    /**
     * 3.发送邮件
     * @throws MessagingException
     */
    public static void sendEmail(MimeMessage message) throws MessagingException{
        Transport.send(message);
    }


}
