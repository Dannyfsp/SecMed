package com.secsystem.emr.user.dto.request;

import com.secsystem.emr.utils.constants.validators.Lowercase;
import com.secsystem.emr.utils.constants.validators.Trimmed;
import com.secsystem.emr.utils.constants.validators.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordRequest {
    @Email(message = "Invalid email format", flags = {Pattern.Flag.CASE_INSENSITIVE})
    @NotBlank(message = "Email is required")
    @NotEmpty(message = "The email address is required.")
    @Trimmed
    @Lowercase
    private String email;
}
