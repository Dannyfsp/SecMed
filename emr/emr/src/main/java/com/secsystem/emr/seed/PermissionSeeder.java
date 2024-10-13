package com.secsystem.emr.seed;


import com.secsystem.emr.shared.models.Permission;
import com.secsystem.emr.shared.models.Role;
import com.secsystem.emr.shared.models.RoleEnum;
import com.secsystem.emr.shared.repository.PermissionRepository;
import com.secsystem.emr.shared.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class PermissionSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        if (permissionRepository.count() > 0) {
            return; // Exit if permissions already seeded
        }

        // Create Permissions
        Permission adminRead = createPermission("ADMIN_READ");
        Permission adminWrite = createPermission("ADMIN_WRITE");
        Permission adminUpdate = createPermission("ADMIN_UPDATE");
        Permission adminDelete = createPermission("ADMIN_DELETE");

        Permission userRead = createPermission("USER_READ");
        Permission userWrite = createPermission("USER_WRITE");
        Permission userUpdate = createPermission("USER_UPDATE");

        Permission superAdminRead = createPermission("SUPER_ADMIN_READ");

        // Create Roles and Assign Permissions
        createRole(RoleEnum.ADMIN, "Admin", Set.of(adminRead, adminWrite, adminUpdate, adminDelete));
        createRole(RoleEnum.USER, "User", Set.of(userRead, userWrite, userUpdate));
        createRole(RoleEnum.NURSE, "Nurse", Set.of(userRead, userWrite, userUpdate));
        createRole(RoleEnum.DOCTOR, "Doctor", Set.of(userRead, userWrite, userUpdate));
        createRole(RoleEnum.PATIENT, "Patient", Set.of(userRead, userWrite, userUpdate));
        createRole(RoleEnum.SPECIALIST, "Specialist", Set.of(userRead, userWrite, userUpdate));
        createRole(RoleEnum.PHARMACY, "Pharmacy", Set.of(userRead, userWrite, userUpdate));
    }

    private Permission createPermission(String name) {
        Permission permission = new Permission(null, name);
        return permissionRepository.save(permission);
    }

    private void createRole(RoleEnum roleName, String description, Set<Permission> permissions) {
        Role role = new Role();
        role.setName(roleName);
        role.setDescription(description);
        role.setPermissions(permissions);
        roleRepository.save(role);
    }
}
