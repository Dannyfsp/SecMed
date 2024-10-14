package com.secsystem.emr.shared.services.impl;

import com.secsystem.emr.exceptions.CustomBadRequestException;
import com.secsystem.emr.exceptions.EntityNotFoundException;
import com.secsystem.emr.shared.dto.OtpRequest;
import com.secsystem.emr.shared.dto.VerifyOtpRequest;
import com.secsystem.emr.shared.models.Otp;
import com.secsystem.emr.shared.models.OtpEnum;
import com.secsystem.emr.shared.repository.OtpRepository;
import com.secsystem.emr.shared.services.OtpService;
import com.secsystem.emr.user.User;
import com.secsystem.emr.user.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class OtpServiceImpl implements OtpService {

    private final OtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public OtpServiceImpl(OtpRepository otpRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.otpRepository = otpRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
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

    @Override
    public void verifyOtp(VerifyOtpRequest verifyOtpRequest) {
        Optional<User> userEmail = userRepository.findByEmail(verifyOtpRequest.getEmail());
        if (userEmail.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        Optional<Otp> otpExist = otpRepository.findByEmailAndPurpose(
                verifyOtpRequest.getEmail(),
                String.valueOf(OtpEnum.USER_REGISTRATION)
        );
        if (otpExist.isEmpty()) {
            throw new EntityNotFoundException("otp not found");
        }

        Otp otp = otpExist.get();

        if (otp.getOtpUsed()) {
                throw new CustomBadRequestException("OTP has already been used");
        }
        if (!passwordEncoder.matches(verifyOtpRequest.getOtpCode(), otp.getOtpCode())) {
            throw new BadCredentialsException("Invalid OTP");
        }
        otp.setOtpUsed(true);
        otpRepository.save(otp);

        User user = userEmail.get();
        user.setIsVerified(true);
        userRepository.save(user);
    }


}
