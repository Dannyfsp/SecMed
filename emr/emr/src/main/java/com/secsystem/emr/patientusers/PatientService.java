package com.secsystem.emr.patientusers;


import com.secsystem.emr.patientusers.dto.PatientSignUpRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public String patientTest(){
        return "Patient test";
    }

    public Patient patientSignUp(PatientSignUpRequest request){
        Patient patientToSave = Patient
                .builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(request.getPassword())
                .build();

        return patientRepository.save(patientToSave);
    }

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }
}
