package com.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.entity.User;

public class EmailUtil {
	
	/**
	 * 获取session实例对象
	 * @return
	 */
	public static Session getSession(){
		Properties props = new Properties();  // 确定连接位置
		props.put("mail.smtp.auth", "true");//
		props.put("mail.transport.protocol", "smtp");// //设置发送的协议
		props.put("mail.host", "smtp.163.com");//获取163邮箱smtp服务器的地址
		
		Session session = Session.getDefaultInstance(props, 
			
				//创建验证器
			new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					// TODO Auto-generated method stub
					//设置发送人的帐号和密码 你的密码
					return new PasswordAuthentication("13673581593@163.com","kht1242915520");
				}
		});
		session.setDebug(true);
		return session;
	}
	
	/**
	 * 发送邮件
	 * @param user
	 * @throws Exception
	 */
	public static void sendEmail(User user) throws Exception {
		Message message = new MimeMessage(getSession());// 创建消息
		message.setFrom(new InternetAddress("13673581593@163.com"));//发件人 
		message.setSubject("找回密码");//主题（标题）
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
		message.setText("尊敬的"+user.getUserName()+": 您好！您在本站登陆的密码为："+user.getPassWord()+" 请保管好你的密码，不要轻易泄露给他(她)人,点此返回登http://localhost:8080/MedicineManSys/");
		Transport.send(message);
	}
}


/**                 收件人 
          *         RecipientType.TO    代表收件人 
          *         RecipientType.CC    抄送
          *         RecipientType.BCC    暗送
          *         比如A要给B发邮件，但是A觉得有必要给要让C也看看其内容，就在给B发邮件时，
          *         将邮件内容抄送给C，那么C也能看到其内容了，但是B也能知道A给C抄送过该封邮件
          *         而如果是暗送(密送)给C的话，那么B就不知道A给C发送过该封邮件。
          *         
          *         收件人的地址，或者是一个Address[]，用来装抄送或者暗送人的名单。或者用来群发。可以是相同邮箱服务器的，也可以是不同的
*/
