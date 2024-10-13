package com.secsystem.emr.shared.repository;

import com.secsystem.emr.shared.models.Role;
import com.secsystem.emr.shared.models.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}