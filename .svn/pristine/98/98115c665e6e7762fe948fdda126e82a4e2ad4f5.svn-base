package org.com.drag.service.impl;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.com.drag.common.util.JavaMailUtils;
import org.com.drag.common.util.MailAuthorSetting;
import org.com.drag.model.EmailDto;
import org.com.drag.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;


/**
 * Created by cdyoue on 2016/9/24.
 */
@Service
public class MailServiceImpl implements MailService {
    private Log log = LogFactory.getLog(MailServiceImpl.class);

    @Autowired
    private  TaskExecutor taskExecutor;




    @Override
    public void sendMailByAsynchronousMode(final EmailDto email) {
        taskExecutor.execute(new Runnable(){
            public void run(){
                try {
                    sendMailBySynchronizationMode(email);
                } catch (Exception e) {
                    log.info(e);
                }
            }
        });
    }


    public void sendMailBySynchronizationMode(EmailDto email) {
        Session session = JavaMailUtils.createSession();
        MimeMessage message;
        try {
            String URL = email.getRemoteAddr();
            message = JavaMailUtils.writeEmail(URL,email.getToken(),session, MailAuthorSetting.USERNAME,email.getEmail(), "优易数据拖拽平台-帐号激活");
            JavaMailUtils.sendEmail(message);
            log.info("注册邮件发送成功！");
        } catch (MessagingException e) {
            e.printStackTrace();
            log.info("邮件发送失败！");
        }
    }

    @Override
    public void sendRetrieveMailByAsynchronousMode(final EmailDto email) {
        taskExecutor.execute(new Runnable(){
            public void run(){
                try {
                    sendRetrieveMailBySynchronizationMode(email);
                } catch (Exception e) {
                    log.info(e);
                }
            }
        });
    }

    public void sendRetrieveMailBySynchronizationMode(EmailDto email) {
        Session session = JavaMailUtils.createSession();
        MimeMessage message;
        try {
            String URL = email.getRemoteAddr();
            message = JavaMailUtils.writeRetrieveEmail(URL,email.getToken(),session, MailAuthorSetting.USERNAME,email.getEmail(), "优易数据拖拽平台-密码修改");
            JavaMailUtils.sendEmail(message);
            log.info("密码找回邮件发送成功！");
        } catch (MessagingException e) {
            e.printStackTrace();
            log.info("密码找回邮件发送失败！");
        }
    }
}
