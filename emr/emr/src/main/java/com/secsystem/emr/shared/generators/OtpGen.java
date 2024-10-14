package com.secsystem.emr.shared.generators;

import java.util.Random;

public class OtpGen {

    private static final int OTP_LENGTH = 6;
    private static final Random random = new Random();

    public static String generateSignUpOtp() {
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    public static String generateForgotPasswordOtp() {
        return generateSignUpOtp();
    }

}
