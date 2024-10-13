package com.secsystem.emr.seed;

import com.secsystem.emr.shared.UserRole;
import com.secsystem.emr.user.User;
import com.secsystem.emr.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0 && userRepository.findByRole(UserRole.ADMIN).isEmpty()) {
            for (int i = 1; i <= 10; i++) {
                User user = new User();
                user.setLastName("user" + i);
                user.setFirstName("user" + i);
                user.setRole(UserRole.USER);
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
