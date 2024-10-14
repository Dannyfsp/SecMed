package com.secsystem.emr.shared.services.impl;

import com.secsystem.emr.shared.dto.OtpRequest;
import com.secsystem.emr.shared.models.Otp;
import com.secsystem.emr.shared.repository.OtpRepository;
import com.secsystem.emr.shared.services.OtpService;
import org.springframework.stereotype.Service;


@Service
public class OtpServiceImpl implements OtpService {

    private final OtpRepository otpRepository;

    public OtpServiceImpl(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    @Override
    public void saveUserOtp(OtpRequest otpRequest) {
        Otp newOtp = Otp
                .builder()
                .email(otpRequest.getEmail())
                .otpCode(otpRequest.getOtpCode())
                .purpose(String.valueOf(otpRequest.getPurpose()))
                .build();

        otpRepository.save(newOtp);
    }


}
