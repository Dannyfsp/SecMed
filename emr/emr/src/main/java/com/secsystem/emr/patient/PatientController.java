package com.secsystem.emr.patient;


import com.secsystem.emr.patient.dto.PatientSignUpRequest;
import com.secsystem.emr.utils.constants.responsehandler.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/test")
    public String testPatient(){
        return patientService.healthCheck();
    }

    @PostMapping("/register")
    public ResponseEntity<?> patientSignUp(@RequestBody @Valid PatientSignUpRequest request){
        Patient patient = patientService.patientSignUp(request);
        return ResponseHandler.responseBuilder("patient created", HttpStatus.CREATED, patient);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Patient>> getPatients(){
        List<Patient> allPatients = patientService.getAll();
        return new ResponseEntity<>(allPatients, HttpStatus.OK);
    }
}
