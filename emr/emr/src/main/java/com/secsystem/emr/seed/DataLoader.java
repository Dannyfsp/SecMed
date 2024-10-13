package com.secsystem.emr.seed;

import com.secsystem.emr.shared.models.Role;
import com.secsystem.emr.shared.models.RoleEnum;
import com.secsystem.emr.shared.repository.RoleRepository;
import com.secsystem.emr.user.User;
import com.secsystem.emr.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

//@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final  RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);
        if (optionalRole.isEmpty()) {
            return;
        }

        if (userRepository.count() == 0 && userRepository.findByRole(RoleEnum.USER).isEmpty()) {
            for (int i = 1; i <= 10; i++) {
                User user = new User();
                user.setLastName("user" + i);
                user.setFirstName("user" + i);
                user.setRole(optionalRole.get());
                user.setPhoneNumber(i + "90" + i + "2323455");
                user.setEmail("user" + i + "@gmail.com");
                user.setAge(2);
                user.setPassword(passwordEncoder.encode("password" + i));
                userRepository.save(user);
            }
            System.out.println("100 users seeded into the database.");
        } else {
            System.out.println("Users already exist in the database.");
        }
    }
}
