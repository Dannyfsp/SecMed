package com.secsystem.emr.patient;


import com.secsystem.emr.exceptions.ConflictException;
import com.secsystem.emr.patient.dto.PatientSignUpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PatientServiceImpl implements PatientService {
    private static  final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);
    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public String healthCheck() {
        return "working";
    }

    @Override
    public Patient patientSignUp(PatientSignUpRequest request) {
        if (patientRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ConflictException("A patient with this email already exists.");
        }
        Patient patientToSave = Patient
                .builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(request.getPassword())
                .build();

        logger.info("User save successfully: {}", patientToSave.getEmail());
        return patientRepository.save(patientToSave);
    }

    @Override
    public List<Patient> getAll() {
        return patientRepository.findAll();
    }
}
