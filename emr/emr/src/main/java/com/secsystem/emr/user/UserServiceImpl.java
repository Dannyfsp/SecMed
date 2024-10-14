package com.secsystem.emr.user;


import com.secsystem.emr.exceptions.ConflictException;
import com.secsystem.emr.exceptions.CustomBadRequestException;
import com.secsystem.emr.exceptions.EntityNotFoundException;
import com.secsystem.emr.shared.dto.OtpRequest;
import com.secsystem.emr.shared.generators.OtpGen;
import com.secsystem.emr.shared.models.Otp;
import com.secsystem.emr.shared.models.OtpEnum;
import com.secsystem.emr.shared.models.Role;
import com.secsystem.emr.shared.models.RoleEnum;
import com.secsystem.emr.shared.repository.OtpRepository;
import com.secsystem.emr.shared.repository.RoleRepository;
import com.secsystem.emr.shared.services.EmailService;
import com.secsystem.emr.shared.services.JwtService;
import com.secsystem.emr.shared.services.OtpService;
import com.secsystem.emr.shared.services.RateLimitingService;
import com.secsystem.emr.user.dto.request.*;
import com.secsystem.emr.user.dto.response.UserLoginResponse;
import com.secsystem.emr.user.dto.response.UserSignUpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final RateLimitingService rateLimitingService;
    private final JwtService jwtService;
    private final OtpService otpService;
    private final OtpRepository otpRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, EmailService emailService, RateLimitingService rateLimitingService, JwtService jwtService, OtpService otpService, OtpRepository otpRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
        this.rateLimitingService = rateLimitingService;
        this.jwtService = jwtService;
        this.otpService = otpService;
        this.otpRepository = otpRepository;
    }

    @Override
    public String healthCheck() {

        String apiKey = "CYBORG";
        if (rateLimitingService.allowRequest(apiKey)) {
            return "working";
        }
        return null;
    }

    @Override
    public UserSignUpResponse userSignUp(SignUpRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ConflictException("A patient with this email already exists.");
        }

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            throw new EntityNotFoundException("Role not found");
        }

        User userToSave = User.builder().email(request.getEmail()).age(request.getAge()).firstName(request.getFirstName()).lastName(request.getLastName()).dateOfBirth(request.getDateOfBirth()).password(passwordEncoder.encode(request.getPassword())).phoneNumber(request.getPhoneNumber()).role(optionalRole.get()).build();

        logger.info("User save successfully: {}", userToSave.getEmail());
        User savedUser = userRepository.save(userToSave);

        String otpCode = OtpGen.generateSignUpOtp();

        String subject = "Please verify your email";
        String text = "Your otp is  " + otpCode;

        OtpRequest otpToSave = OtpRequest.builder()
                .email(userToSave.getEmail())
                .otpCode(passwordEncoder.encode(otpCode))
                .purpose(OtpEnum.USER_REGISTRATION)
                .build();
        otpService.saveUserOtp(otpToSave);
        emailService.sendEmail(userToSave.getEmail(), subject, text);

        return UserSignUpResponse.builder()
                .email(savedUser.getEmail())
                .role(savedUser.getRole().getName().toString())
                .id(savedUser.getId())
                .build();
    }

    @Override
    public UserLoginResponse loginUser(LoginRequest request) {
        Optional<User> userExist = userRepository.findByEmail(request.getEmail());
        if (userExist.isEmpty()) {
            throw new EntityNotFoundException("User does not exist");
        }

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        if (!authenticate.isAuthenticated()) {
            throw new BadCredentialsException("Invalid username or password");
        }

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return UserLoginResponse.builder().email(user.getEmail()).token(jwt).refreshToken(refreshToken).build();

    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest, Principal principal) {
        Optional<User> optionalUser = userRepository.findByEmail(principal.getName());
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        User user = optionalUser.get();
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User with this email does not exist");
        }

        String forgotPasswordCode = OtpGen.generateForgotPasswordOtp();
        String subject = "Forgot Password";
        String text = "Your otp is  " + forgotPasswordCode;
        String userEmail = user.get().getEmail();
        OtpRequest otpToSave = OtpRequest.builder()
                .email(userEmail)
                .otpCode(passwordEncoder.encode(forgotPasswordCode))
                .purpose(OtpEnum.FORGOT_PASSWORD)
                .build();
        otpService.saveUserOtp(otpToSave);
        emailService.sendEmail(userEmail, subject, text);

    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User with this email does not exist");
        }
        User user = userOptional.get();

        Optional<Otp> storedOtp = otpRepository.findByEmailAndPurpose(user.getEmail(), String.valueOf(OtpEnum.FORGOT_PASSWORD));
        if (storedOtp.isEmpty()) {
            throw new CustomBadRequestException("Invalid or expired OTP");
        }

        Otp otp = storedOtp.get();

        if (!passwordEncoder.matches(request.getOtpCode(), otp.getOtpCode())) {
            throw new BadCredentialsException("Invalid OTP entered");
        }

        otpRepository.delete(otp);

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }





}
