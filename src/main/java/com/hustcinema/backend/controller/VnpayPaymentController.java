package com.hustcinema.backend.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hustcinema.backend.service.PaymentService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/vnpay")
public class VnpayPaymentController {

    @Autowired
    private PaymentService paymentService;
    @PostMapping("/payment")
    public String pay(HttpServletRequest request) throws UnsupportedEncodingException {
        return paymentService.createVnPayPayment(request);
    }

    @GetMapping("/respond")
    public ResponseEntity<String> closeTab(HttpServletRequest request) {

        String status = request.getParameter("vnp_ResponseCode");
        String closeTabHtml = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\">" +
                "<title>Close Tab</title></head><body>" +
                "<script type=\"text/javascript\">" +
                "function closeCurrentTab() {" +
                "window.open('', '_self', '');" +
                "window.close();" +
                "}" +
                "setTimeout(closeCurrentTab, 1);" +
                "</script><p>Processing payment, please wait...</p></body></html>";

        if (status.equals("00")) {
            return ResponseEntity.status(488).body(closeTabHtml);
        } 
        else if(status.equals("24")) {
            return ResponseEntity.status(499).body(closeTabHtml);
        }
        else {
            return ResponseEntity.status(500).body(closeTabHtml);
        }
    }
}