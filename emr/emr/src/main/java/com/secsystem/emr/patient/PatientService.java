package com.secsystem.emr.patient;


import com.secsystem.emr.patient.dto.PatientSignUpRequest;
import java.util.List;


public interface PatientService {
    public String healthCheck();
    public Patient patientSignUp(PatientSignUpRequest request);
    public List<Patient> getAll();

}
