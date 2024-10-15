package com.secsystem.emr.user;


import com.secsystem.emr.shared.models.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByRole(RoleEnum roleEnum);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
}
