package com.secsystem.emr.user;


import com.secsystem.emr.shared.models.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByRole(RoleEnum roleEnum);
    boolean existsByPhoneNumber(String phoneNumber);
}
