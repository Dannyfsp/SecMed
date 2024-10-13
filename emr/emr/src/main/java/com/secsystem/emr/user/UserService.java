package com.secsystem.emr.user;


import com.secsystem.emr.user.dto.request.LoginRequest;
import com.secsystem.emr.user.dto.request.SignUpRequest;
import com.secsystem.emr.user.dto.response.UserLoginResponse;
import com.secsystem.emr.user.dto.response.UserSignUpResponse;

import java.util.List;


public interface UserService {
    String healthCheck();
    UserSignUpResponse userSignUp(SignUpRequest request);
    UserLoginResponse loginUser(LoginRequest request);
}
