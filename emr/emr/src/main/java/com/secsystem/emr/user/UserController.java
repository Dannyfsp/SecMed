package com.secsystem.emr.user;


import com.secsystem.emr.shared.SecurityUtils;
import com.secsystem.emr.user.dto.request.ChangePasswordRequest;
import com.secsystem.emr.user.dto.request.LoginRequest;
import com.secsystem.emr.user.dto.request.SignUpRequest;
import com.secsystem.emr.user.dto.response.UserLoginResponse;
import com.secsystem.emr.user.dto.response.UserSignUpResponse;
import com.secsystem.emr.utils.constants.responsehandler.ResponseHandler;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("${api.prefix}/auth")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    @Hidden
    public String testPatient(){
        return userService.healthCheck();
    }

    @PostMapping("/register")
    public ResponseEntity<?> userSignUp(@RequestBody @Valid SignUpRequest request){
        UserSignUpResponse response = userService.userSignUp(request);
        return ResponseHandler.responseBuilder("user created", HttpStatus.CREATED, response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody @Valid LoginRequest request){
        UserLoginResponse response = userService.loginUser((request));
        return ResponseHandler.responseBuilder("user login successfully", HttpStatus.OK, response);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> authenticatedUser() {
        User currentUser = SecurityUtils.getLoggedInUser();
        logger.info("User Id::===================== :" + String.valueOf(currentUser.getId()));
        return ResponseHandler.responseBuilder("user profile retrieved", HttpStatus.OK, currentUser);
    }


    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest changePasswordRequest) {
        {
            userService.changePassword(changePasswordRequest, SecurityUtils.getAuthentication());
            return ResponseHandler.responseBuilder("user password changed successfully", HttpStatus.OK, null);
        }
    }

}
