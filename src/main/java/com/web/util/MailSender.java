package com.web.util;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.scheduling.annotation.Async;

import com.web.entity.Ticket;

/**
 * SendMail
 */
@Async
public class MailSender {

    public static void SendMail(Ticket ticket) {
    	StringBuilder data = new StringBuilder();
    	data.append("电影名：");
    	data.append(ticket.getMovieName());
    	data.append(" 上映时间：");
    	data.append(ticket.getScreenDate());
    	data.append(" ");
    	data.append(ticket.getScreenTime());
    	data.append(" 影厅：");
    	data.append(ticket.getVideoHall());
    	data.append(" 座位：");
    	data.append(ticket.getRow());
    	data.append(ticket.getSeat());
    	data.append(" 取票ID：");
    	data.append(ticket.getTicketId());
    	String qrCodeFileUrl = QRCodeUtil.getQRCode(data.toString(),ticket.getTicketId());
    	ticket.setQrCodeFileUrl(qrCodeFileUrl);
		try {
			Properties prop = new Properties();
			prop.setProperty("mail.host", "smtp.163.com");
			prop.setProperty("mail.transport.protocol", "smtp");
			prop.setProperty("mail.smtp.auth", "true");
			//使用JavaMail发送邮件的5个步骤
			//1、创建session
			Session session = Session.getInstance(prop);
			//开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
			session.setDebug(true);
			//2、通过session得到transport对象
			Transport ts = session.getTransport();
			//3、连上邮件服务器，需要发件人提供邮箱的用户名和密码进行验证
	        ts.connect("smtp.163.com", "13407547940@163.com", "aptx4869");
	        //4、创建邮件
//	        Message message = createSimpleMail(session,ticket);
	        Message message = getMimeMessage(session,ticket);
	        //5、发送邮件
	        ts.sendMessage(message, message.getAllRecipients());
	        //删除二维码图片
	        File file = new File(ticket.getQrCodeFileUrl());
	        file.delete();
	        ts.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     *  创建一封只包含文本的邮件
     */ 
     public static MimeMessage createSimpleMail(Session session,Ticket ticket) throws Exception {
         //创建邮件对象
         MimeMessage message = new MimeMessage(session);
         //指明邮件的发件人
         message.setFrom(new InternetAddress("13407547940@163.com"));
         //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
         message.setRecipient(Message.RecipientType.TO, new InternetAddress(ticket.getEmail()));
         //邮件的标题
         message.setSubject("您有一张电影票支付订单");
         //邮件的文本内容
         message.setContent("<div style=\"width:330px;border:1px solid orange;padding:5px;\">\r\n" + 
         		"       			<div>\r\n" + 
         		"       				<div style=\"width:10px;height:15px;background-color:orange;display:inline-block;\"></div>\r\n" + 
         		"       				<h4 style=\"display:inline\"><font color=\"orange\">NetFilx</font></h4>\r\n" + 
         		"       				<h4 style=\"display:inline\">电影</h4>\r\n" + 
         		"       				<h3>"+ticket.getCinemaName()+"</h3>\r\n" + 
         		"       				<h4>影片 : "+ticket.getMovieName()+"</h4>\r\n" + 
         		"       				<h4>影厅 : "+ticket.getVideoHall()+"</h4>\r\n" + 
         		"       				<h3>"+ticket.getRow()+"&nbsp"+ticket.getSeat()+"&nbsp"+ticket.getScreenDate()+"&nbsp"+ticket.getScreenTime()+"</h3>\r\n" + 
         		"       				<h6>票价 : "+ticket.getPrice()+"</h6>\r\n" + 
         		"       				<h6>票类 :  网络售票</h6>\r\n" + 
         		"       				<h6>取票码 : "+ticket.getTicketId()+"</h6>\r\n" + 
         		"       			</div>\r\n" + 
         		"       			<div style=\"width:100%;height:20px;background-color:orange;\">\r\n" + 
         		"       				<p style=\"color:white\">&nbsp客服热线: 4000-406-842 &nbsp Netflix电影</p>\r\n" + 
         		"       			</div>\r\n" + 
         		"       		</div>", "text/html;charset=UTF-8");
         
         //返回创建好的邮件对象
         return message;
     }
     
     
     /**
      *	 获得创建一封邮件的实例对象
      */
     public static MimeMessage getMimeMessage(Session session,Ticket ticket) throws Exception{
         //1.创建一封邮件的实例对象
         MimeMessage msg = new MimeMessage(session);
         //2.设置发件人地址
         msg.setFrom(new InternetAddress("13407547940@163.com"));
         /**
          * 3.设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
          * MimeMessage.RecipientType.TO:发送
          * MimeMessage.RecipientType.CC：抄送
          * MimeMessage.RecipientType.BCC：密送
          */
         msg.addRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse("13407547940@163.com"));
         msg.setRecipient(Message.RecipientType.TO, new InternetAddress(ticket.getEmail()));
         //4.设置邮件主题
         msg.setSubject("您有一张电影票支付订单");
          
         //下面是设置邮件正文
          
         // 5. 创建图片"节点"
         MimeBodyPart image = new MimeBodyPart();
         // 读取本地文件
         DataHandler dh = new DataHandler(new FileDataSource(ticket.getQrCodeFileUrl()));
         // 将图片数据添加到"节点"
         image.setDataHandler(dh);
         // 为"节点"设置一个唯一编号（在文本"节点"将引用该ID）
         image.setContentID("mailPic");    
          
         // 6. 创建文本"节点"
         MimeBodyPart text = new MimeBodyPart();
         // 这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
         text.setContent("<div style=\"width:330px;border:1px solid orange;padding:5px;\">\r\n" + 
          		"       			<div>\r\n" + 
          		"       				<div style=\"width:10px;height:15px;background-color:orange;display:inline-block;\"></div>\r\n" + 
          		"       				<h4 style=\"display:inline\"><font color=\"orange\">NetFilx</font></h4>\r\n" + 
          		"       				<h4 style=\"display:inline\">电影</h4>\r\n" + 
          		"       				<h3>"+ticket.getCinemaName()+"</h3>\r\n" + 
          		"       				<h4>影片 : "+ticket.getMovieName()+"</h4>\r\n" + 
          		"       				<h4>影厅 : "+ticket.getVideoHall()+"</h4>\r\n" + 
          		"       				<h3>"+ticket.getRow()+"&nbsp"+ticket.getSeat()+"&nbsp"+ticket.getScreenDate()+"&nbsp"+ticket.getScreenTime()+"</h3>\r\n" + 
          		"                       <img style=\"float:right\" src='cid:mailPic'/>"+
          		"       				<h6>票价 : "+ticket.getPrice()+"</h6>\r\n" + 
          		"       				<h6>票类 :  网络售票</h6>\r\n" + 
          		"       				<h6>取票码 : "+ticket.getTicketId()+"</h6>\r\n" + 
          		"       			</div>\r\n" + 
          		"       			<div style=\"width:100%;height:20px;background-color:orange;\">\r\n" + 
          		"       				<p style=\"color:white\">&nbsp客服热线: 4000-406-842 &nbsp Netflix电影</p>\r\n" + 
          		"       			</div>\r\n" + 
          		"       		</div>", "text/html;charset=UTF-8");
         
         // 7. （文本+图片）设置 文本 和 图片"节点"的关系（将 文本 和 图片"节点"合成一个混合"节点"）
         MimeMultipart mm_text_image = new MimeMultipart();
         mm_text_image.addBodyPart(text);
         mm_text_image.addBodyPart(image);
         mm_text_image.setSubType("related");    // 关联关系
          
         // 8. 将 文本+图片 的混合"节点"封装成一个普通"节点"
         // 最终添加到邮件的 Content 是由多个 BodyPart 组成的 Multipart, 所以我们需要的是 BodyPart,
         // 上面的 mailTestPic 并非 BodyPart, 所有要把 mm_text_image 封装成一个 BodyPart
         MimeBodyPart text_image = new MimeBodyPart();
         text_image.setContent(mm_text_image);
         MimeMultipart mm = new MimeMultipart();
         mm.addBodyPart(text_image);
         // 11. 设置整个邮件的关系（将最终的混合"节点"作为邮件的内容添加到邮件对象）
         msg.setContent(mm);
         //设置邮件的发送时间,默认立即发送
         msg.setSentDate(new Date());
          
         return msg;
     }
     
}