package com.secsystem.emr.user;


import com.secsystem.emr.user.dto.request.ChangePasswordRequest;
import com.secsystem.emr.user.dto.request.LoginRequest;
import com.secsystem.emr.user.dto.request.SignUpRequest;
import com.secsystem.emr.user.dto.response.UserLoginResponse;
import com.secsystem.emr.user.dto.response.UserSignUpResponse;

import java.security.Principal;


public interface UserService {
    String healthCheck();
    UserSignUpResponse userSignUp(SignUpRequest request);
    UserLoginResponse loginUser(LoginRequest request);
    void changePassword(ChangePasswordRequest changePasswordRequest, Principal principal);
    User findByEmail(String email);
}
