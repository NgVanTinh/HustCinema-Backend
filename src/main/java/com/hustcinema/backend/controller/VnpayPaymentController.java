package com.hustcinema.backend.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hustcinema.backend.service.PaymentService;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
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
    public String payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            return "Success";
        } else {
            return "Failed";
        }
    }
}