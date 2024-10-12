package com.secsystem.emr.patientusers.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientSignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
