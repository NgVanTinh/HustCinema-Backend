// package com.hustcinema.backend.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.hustcinema.backend.service.MailService;
// import static com.hustcinema.backend.utils.TimeStamp.genOTP;

// @RestController
// public class GetOTP {
//     @Autowired
//     private MailService emailSender;

//     @PostMapping("/getOTP")
//     public String getOTP(@RequestParam String toEmail) {
//         String otp = genOTP(toEmail);
//         String subject = "Active your account";
//         String body = "This is your OTP: " + otp + ".\n The OTP with expire in 3 minutes";
//         if (emailSender.sendEmail(toEmail, subject, body)) {
//             return "OTP sent successfully";
//         } else {
//             return "Sent OTP fail, please try again!";
//         }
//     } 
// }
