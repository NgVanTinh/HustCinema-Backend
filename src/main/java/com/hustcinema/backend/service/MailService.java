// package com.hustcinema.backend.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.mail.MailException;
// import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.stereotype.Service;


// @Service
// public class MailService {
//     @Autowired
//     private JavaMailSender mailSender;

//     public boolean sendEmail(String toEmail, String subject, String body) {
//         SimpleMailMessage message = new SimpleMailMessage();
//         message.setFrom("nguyen.tinh.8113@gmail.com");
//         message.setTo(toEmail);
//         message.setText(body);
//         message.setSubject(subject);

//         try {
//             mailSender.send(message);
//             System.out.println("Email sent successfully");
//             return true;
//         } catch (MailException e) {
//             System.out.println("Failed to send email: " + e.getMessage());
//             return false;
//         }
//     }
// }
