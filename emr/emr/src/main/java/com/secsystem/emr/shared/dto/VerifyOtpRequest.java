package com.secsystem.emr.shared.dto;

import com.secsystem.emr.shared.models.OtpEnum;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyOtpRequest {
    private String email;
    private String otpCode;
    private OtpEnum purpose;
}
