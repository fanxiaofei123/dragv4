package email;

import com.sun.mail.smtp.SMTPSendFailedException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 用于发送邮件
 */
public class EmailSendTool {
	// 邮箱服务器
	private String host;
	// 这个是你的邮箱用户名
	private String username;
	// 你的邮箱密码
	private String password = "";

	private String mail_head_name = "this is head of this mail";

	private String mail_head_value = "this is head of this mail";

	private String mail_to;

	private String mail_from = "";
	//邮箱主题
	private String mail_subject = "this is the subject of this test mail";
	//设置邮件正文
	private String mail_body = "this is the mail_body of this test mail";

	private String personalName = "";

	public EmailSendTool() {
	}

	public EmailSendTool(String host, String username, String password,
						 String mailto, String subject, String text, String name,
						 String head_name, String head_value) {
		this.host = host;
		this.username = username;
		this.mail_from = username;
		this.password = password;
		this.mail_to = mailto;
		this.mail_subject = subject;
		this.mail_body = text;
		this.personalName = name;
		this.mail_head_name = head_name;
		this.mail_head_value = head_value;
	}

	/**
	 * 此段代码用来发送普通电子邮件
	 *
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 * @throws UnsupportedEncodingException
	 */
	public void send() throws MessagingException, UnsupportedEncodingException,SMTPSendFailedException {


		String from = mail_from;// 发件人电子邮箱
//		String host = host; // 指定发送邮件的主机smtp.qq.com(QQ)|smtp.163.com(网易)

		Properties properties = System.getProperties();// 获取系统属性

		properties.setProperty("mail.smtp.host", host);// 设置邮件服务器 启用这个就是QQ的
//		properties.setProperty("mail.smtp.host", "smtp.163.com");// 设置邮件服务器
		properties.setProperty("mail.smtp.auth", "true");// 打开认证

		try {
			//QQ邮箱需要下面这段代码，163邮箱不需要
//			MailSSLSocketFactory sf = new MailSSLSocketFactory();
//			sf.setTrustAllHosts(true);
//			properties.put("mail.smtp.ssl.enable", "true");
//			properties.put("mail.smtp.ssl.socketFactory", sf);


			// 1.获取默认session对象
			Session session = Session.getDefaultInstance(properties, new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(mail_from, password); // 发件人邮箱账号、授权码
//					return new PasswordAuthentication("186572323507@163.com", "guzhendong990"); // 发件人邮箱账号、授权码
				}
			});

			// 2.创建邮件对象
			Message message = new MimeMessage(session);
			// 2.1设置发件人
			message.setFrom(new InternetAddress(from));
			// 2.2设置接收人
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail_to));
			// 2.3设置邮件主题
			message.setSubject(mail_subject);
			// 2.4设置邮件内容
			String content = mail_body;
			message.setContent(content, "text/html;charset=UTF-8");
			// 3.发送邮件
			Transport.send(message);
//			System.out.println("邮件成功发送!");
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	/**
	 * 用来进行服务器对用户的认证
	 */
	public class Email_Autherticator extends Authenticator {
		public Email_Autherticator() {
			super();
		}

		public Email_Autherticator(String user, String pwd) {
			super();
			username = user;
			password = pwd;
		}

		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail_head_name() {
		return mail_head_name;
	}

	public void setMail_head_name(String mail_head_name) {
		this.mail_head_name = mail_head_name;
	}

	public String getMail_head_value() {
		return mail_head_value;
	}

	public void setMail_head_value(String mail_head_value) {
		this.mail_head_value = mail_head_value;
	}

	public String getMail_to() {
		return mail_to;
	}

	public void setMail_to(String mail_to) {
		this.mail_to = mail_to;
	}

	public String getMail_from() {
		return mail_from;
	}

	public void setMail_from(String mail_from) {
		this.mail_from = mail_from;
	}

	public String getMail_subject() {
		return mail_subject;
	}

	public void setMail_subject(String mail_subject) {
		this.mail_subject = mail_subject;
	}

	public String getMail_body() {
		return mail_body;
	}

	public void setMail_body(String mail_body) {
		this.mail_body = mail_body;
	}

	public String getPersonalName() {
		return personalName;
	}

	public void setPersonalName(String personalName) {
		this.personalName = personalName;
	}

	public static void main(String[] args) {
		EmailSendTool sendEmail = new EmailSendTool("smtp.163.com", "18657111507@163.com", "guzhendong990", "448100031@qq.com",
				"请让我通过", "你的模型不好你的模型不好你的模型不好你的模型不好", "gzd", "dsadsa", "dsda");
		try {
			sendEmail.send();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}