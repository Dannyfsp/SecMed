package com.secsystem.emr.seed;

import com.secsystem.emr.shared.models.Role;
import com.secsystem.emr.shared.models.RoleEnum;
import com.secsystem.emr.shared.repository.RoleRepository;
import com.secsystem.emr.user.User;
import com.secsystem.emr.user.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

//@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AdminSeeder(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder, RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator();
    }

    private void createSuperAdministrator() {
        String email = "super.admin@email.com";
        Optional<User> optionalUser = userRepository.findByEmail(email);
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ADMIN);
        if (optionalUser.isPresent()) {
            return;
        }

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        User superAdmin = User.builder()
                .firstName("Super")
                .lastName("Admin")
                .email(email)
                .password(passwordEncoder.encode("1234567"))
                .phoneNumber("1234567890")
                .age(30)
                .role(optionalRole.get())
                .build();
        userRepository.save(superAdmin);
    }
}
