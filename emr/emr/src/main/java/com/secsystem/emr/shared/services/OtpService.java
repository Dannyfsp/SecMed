package com.secsystem.emr.shared.services;

import com.secsystem.emr.shared.dto.OtpRequest;
import com.secsystem.emr.shared.dto.VerifyOtpRequest;
import com.secsystem.emr.shared.models.Otp;

public interface OtpService {
    void saveUserOtp(OtpRequest otpRequest);
    void verifyOtp(VerifyOtpRequest verifyOtpRequest);
}
