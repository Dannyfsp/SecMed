package com.secsystem.emr.patientusers.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientLoginRequest {
    private String email;
    private String password;
}
