package com.secsystem.emr.user;


import com.secsystem.emr.exceptions.ConflictException;
import com.secsystem.emr.exceptions.EntityNotFoundException;
import com.secsystem.emr.shared.UserRole;
import com.secsystem.emr.shared.services.JwtService;
import com.secsystem.emr.user.dto.request.LoginRequest;
import com.secsystem.emr.user.dto.request.SignUpRequest;
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

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public String healthCheck() {
        return "working";
    }

    @Override
    public UserSignUpResponse userSignUp(SignUpRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ConflictException("A patient with this email already exists.");
        }
        User patientToSave = User
                .builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .role(UserRole.USER)
                .build();

        logger.info("User save successfully: {}", patientToSave.getEmail());
        User savedUser = userRepository.save(patientToSave);
        UserSignUpResponse response = UserSignUpResponse
                .builder()
                .email(savedUser.getEmail())
                .role(savedUser.getRole().toString())
                .id(savedUser.getId())
                .build();

        return response;
    }

    @Override
    public UserLoginResponse loginUser(LoginRequest request) {
        Optional<User> userExist = userRepository.findByEmail(request.getEmail());
        if(userExist.isEmpty()) {
            throw new EntityNotFoundException("User does not exist");
        }

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        if(!authenticate.isAuthenticated()) {
            throw new BadCredentialsException("Invalid username or password");
        }

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwt = jwtService.generateToken(user);

        return UserLoginResponse.builder()
                .email(user.getEmail())
                .token(jwt)
//                .refreshToken(refreshToken.getRefreshToken())
                .build();

    }

}
