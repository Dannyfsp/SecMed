package com.secsystem.emr.seed;

import com.secsystem.emr.shared.UserRole;
import com.secsystem.emr.user.User;
import com.secsystem.emr.user.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator();
    }

    private void createSuperAdministrator() {
        String email = "super.admin@email.com";

        // Check if the user already exists
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return; // User already exists, no need to create
        }

        // Create a new super admin user if none exists
        User superAdmin = User.builder()
                .firstName("Super")
                .lastName("Admin")
                .email(email)
                .password(passwordEncoder.encode("1234567")) // Encode the password
                .phoneNumber("1234567890") // Add a phone number (required)
                .age(30) // Set an appropriate age
                .role(UserRole.ADMIN) // Set role to SUPER_ADMIN
                .build();

        // Save the user
        userRepository.save(superAdmin);
    }
}
