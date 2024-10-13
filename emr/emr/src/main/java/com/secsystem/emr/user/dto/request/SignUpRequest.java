package com.secsystem.emr.user.dto.request;


import com.secsystem.emr.utils.constants.validators.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @Email(message = "Invalid email format", flags = {Pattern.Flag.CASE_INSENSITIVE})
    @NotBlank(message = "Email is required")
    @NotEmpty(message = "The email address is required.")
    @Trimmed
    @UniqueEmail
    private String email;

    @NotBlank(message = "First name is required")
    @NotEmpty(message = "The first name is required.")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @NotEmpty(message = "The last name is required.")
    private String lastName;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Password is required")
    @UniquePhone
    @CorrectNumber
    private String phoneNumber;

    @NotNull(message = "dateOfBirth should not be null")
    @ValidDateOfBirth(minAge = 18)
    private LocalDate dateOfBirth;

    @Positive(message = "Age must be positive")
    private Integer age;

}
