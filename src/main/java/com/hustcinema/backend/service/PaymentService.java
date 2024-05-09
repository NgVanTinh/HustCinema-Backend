package com.hustcinema.backend.service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.hustcinema.backend.config.payment.PaymentConfig;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class PaymentService {
    @Autowired
    private PaymentConfig vnPayConfig;
    // @PreAuthorize("hasRole('USER')")
    public String createVnPayPayment(HttpServletRequest request) throws UnsupportedEncodingException {
        HttpSession session = request.getSession();
        long amount = (Long) session.getAttribute("totalPrice") * 100L;
        // String bankCode = "NCB";
        Map<String, String> vnpParamsMap = new HashMap<String, String>();
        vnpParamsMap.put("vnp_Version", "2.1.0");
        vnpParamsMap.put("vnp_Command", "pay");
        vnpParamsMap.put("vnp_TmnCode", vnPayConfig.getVnp_TmnCode());
        vnpParamsMap.put("vnp_CurrCode", "VND");
        vnpParamsMap.put("vnp_TxnRef",  PaymentConfig.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan don hang:" +  PaymentConfig.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderType", "other");
        vnpParamsMap.put("vnp_Locale", "vn");
        vnpParamsMap.put("vnp_ReturnUrl", PaymentConfig.vnp_ReturnUrl);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_CreateDate", vnpCreateDate);
        calendar.add(Calendar.MINUTE, 10);
        String vnp_ExpireDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_ExpireDate", vnp_ExpireDate);
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        // if (bankCode != null && !bankCode.isEmpty()) {
        //     vnpParamsMap.put("vnp_BankCode", bankCode);
        // }
        vnpParamsMap.put("vnp_IpAddr", PaymentConfig.getIpAddress(request));
        //build query url
        String queryUrl = PaymentConfig.getPaymentURL(vnpParamsMap, true);
        String hashData = PaymentConfig.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = PaymentConfig.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = PaymentConfig.vnp_PayUrl + "?" + queryUrl;
        return paymentUrl;
    }
}
