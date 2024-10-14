package com.secsystem.emr.shared.repository;

import com.secsystem.emr.shared.dto.VerifyOtpRequest;
import com.secsystem.emr.shared.models.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Integer> {
    Optional<Otp> findByEmailAndPurpose(String email, String purpose);

}
