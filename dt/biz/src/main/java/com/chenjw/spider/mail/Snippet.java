package com.chenjw.spider.mail;

public class Snippet {
	public static void main(String[] args) {
		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("13516808180@163.com");
		mailInfo.setPassword("showmethemoney");// 您的邮箱密码
		mailInfo.setFromAddress("13516808180@163.com");
		mailInfo.setToAddress("13516808180@163.com");
		mailInfo.setSubject("设置邮箱标题 如http://www.guihua.org 中国桂花网");
		mailInfo.setContent("设置邮箱内容 如http://www.guihua.org 中国桂花网 是中国最大桂花网站==");
		// 这个类主要来发送邮件
		SimpleMailSender.sendTextMail(mailInfo);// 发送文体格式
		SimpleMailSender.sendHtmlMail(mailInfo);// 发送html格式
		System.out.println("finished!");
	}
}