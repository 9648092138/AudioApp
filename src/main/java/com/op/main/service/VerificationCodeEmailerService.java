package com.op.main.service;

import java.io.File;
import java.util.UUID;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.op.main.model.User;



@Service
public class VerificationCodeEmailerService {
	@Autowired
	private JavaMailSender emailSender;
	
	//@Autowired
	//private TemplateService templateService;

	@Value("${track.sender.emailaddress}")
	private String fromEmailAddress;

	@Value("${track.verificationmail.subject}")
	private String subject;

	
	

	public void sendSimpleMessage(User result) {
		try {
		UUID userId = result.getUserId();
		String toEmail = result.getEmail();
		String username =result.getName();
		String content ="Your Registration successfully"
				+ ""+" Your registration id is \n "+userId+" and Username is \n "+username;
		   MimeMessage message = emailSender.createMimeMessage();
	 		MimeMessageHelper helper = new MimeMessageHelper(message,true);
	 		System.out.println("token "+content);
	 		
			helper.setFrom(fromEmailAddress);
			helper.setTo(toEmail);
			helper.setSubject(subject);
		
			helper.setText(content,true);
		
			// let's attach the infamous windows Sample file (this time copied to c:/)
			FileSystemResource file = new FileSystemResource(new File("/home/faizan/Desktop/java.png"));
			helper.addAttachment("download.jpeg", file);
			System.out.println(message.toString());
			System.out.println("This is message----->" + message.toString());
			
			
			emailSender.send(message);
			System.out.println("email send ----->" + message);
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.print(e);
		}
	}
}

