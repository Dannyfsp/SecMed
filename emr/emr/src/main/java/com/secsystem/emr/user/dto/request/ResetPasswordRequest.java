package com.secsystem.emr.user.dto.request;

import com.secsystem.emr.utils.constants.validators.Lowercase;
import com.secsystem.emr.utils.constants.validators.Trimmed;
import com.secsystem.emr.utils.constants.validators.UniqueEmail;
import jakarta.validation.constraints.*;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Confirm Password is required")
    private String confirmPassword;

    @Email(message = "Invalid email format", flags = {Pattern.Flag.CASE_INSENSITIVE})
    @NotBlank(message = "Email is required")
    @NotEmpty(message = "The email address is required.")
    @Trimmed
    @Lowercase
    private String email;

    @NotBlank(message = "Otp is required")
    @Size(min = 6, message = "Otp must be 6 characters long")
    private String otpCode;

}
