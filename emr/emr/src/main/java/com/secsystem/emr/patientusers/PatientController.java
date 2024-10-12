package com.secsystem.emr.patientusers;


import com.secsystem.emr.patientusers.dto.PatientSignUpRequest;
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
        return patientService.patientTest();
    }

    @PostMapping("/register")
    public ResponseEntity<Patient> patientSignUp(@RequestBody PatientSignUpRequest request){
        Patient patient = patientService.patientSignUp(request);
        return new ResponseEntity<>(patient, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Patient>> getPatients(){
        List<Patient> allPatients = patientService.getAll();
        return new ResponseEntity<>(allPatients, HttpStatus.OK);
    }
}
