package com.hustcinema.backend.utils;

import static com.hustcinema.backend.utils.Hash.sha256;

public class TimeStamp {
    public static String getTimeX() {
        long unixTimestamp = System.currentTimeMillis() / 1000;
        int result = (int) (unixTimestamp / 180) % 1000000;
        return String.valueOf(result);
    }

    public static String genOTP(String email) {
        String hash = sha256(email + getTimeX());
        String otp = hash.substring(0, 6);
        return otp;
    }
}
