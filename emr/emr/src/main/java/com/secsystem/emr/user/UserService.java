package com.secsystem.emr.user;


import com.secsystem.emr.user.dto.request.*;
import com.secsystem.emr.user.dto.response.UserLoginResponse;
import com.secsystem.emr.user.dto.response.UserSignUpResponse;

import java.security.Principal;


public interface UserService {
    String healthCheck();
    UserSignUpResponse userSignUp(SignUpRequest request);
    UserLoginResponse loginUser(LoginRequest request);
    void changePassword(ChangePasswordRequest changePasswordRequest, Principal principal);
    User findByEmail(String email);
    void forgotPassword(ForgotPasswordRequest request);
    void resetPassword(ResetPasswordRequest request);
}
