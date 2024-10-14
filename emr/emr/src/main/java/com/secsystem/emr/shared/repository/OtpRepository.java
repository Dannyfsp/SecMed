package com.secsystem.emr.shared.repository;

import com.secsystem.emr.shared.dto.VerifyOtpRequest;
import com.secsystem.emr.shared.models.Otp;
import com.secsystem.emr.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Integer> {
    @Query("SELECT o FROM Otp o WHERE o.email = :email AND o.purpose = :purpose")
    Optional<Otp> findByEmailAndPurpose(String email, String purpose);

    @Query("SELECT o FROM Otp o WHERE o.otpCode = :otpCode AND o.purpose = :purpose")
    Optional<Otp> findOtpByOtpCodeAndPurpose(String otpCode, String purpose);
}
