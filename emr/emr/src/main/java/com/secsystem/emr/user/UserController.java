package com.secsystem.emr.user;


import com.secsystem.emr.user.dto.request.LoginRequest;
import com.secsystem.emr.user.dto.request.SignUpRequest;
import com.secsystem.emr.user.dto.response.UserLoginResponse;
import com.secsystem.emr.user.dto.response.UserSignUpResponse;
import com.secsystem.emr.utils.constants.responsehandler.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String testPatient(){
        return userService.healthCheck();
    }

    @PostMapping("/register")
    public ResponseEntity<?> userSignUp(@RequestBody @Valid SignUpRequest request){
        UserSignUpResponse response = userService.userSignUp(request);
        return ResponseHandler.responseBuilder("user created", HttpStatus.CREATED, response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLoginp(@RequestBody @Valid LoginRequest request){
        UserLoginResponse response = userService.loginUser((request));
        return ResponseHandler.responseBuilder("user login successfully", HttpStatus.OK, response);
    }

}
