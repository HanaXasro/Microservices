package com.erp.userservice.seeders;

import com.erp.commonlib.helper.PermissionHelper;
import com.erp.userservice.entities.Permission;
import com.erp.userservice.entities.Role;
import com.erp.userservice.entities.RolePermission;
import com.erp.userservice.entities.User;
import com.erp.userservice.repositories.IRole;
import com.erp.userservice.repositories.IRolePermission;
import com.erp.userservice.repositories.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.erp.userservice.repositories.IPermission;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;


@Component
public class UserRolePermissionSeeder implements CommandLineRunner {

        @Autowired
        private IPermission permissionRepository;

        @Autowired
        private IUser userRepository;

        @Autowired
        private IRolePermission rolePermissionRepository;

        @Autowired
        private IRole roleRepository;

        @Override
        public void run(String... args) throws Exception {
            // 1. Get all permissions from helper
            List<String> permissions = PermissionHelper.getAllPermissions();
            List<Permission> savedPermissions = new ArrayList<>();

            for (String permissionTitle : permissions) {
                Permission permission = permissionRepository.findAll()
                        .stream()
                        .filter(p -> p.getTitle().equals(permissionTitle))
                        .findFirst()
                        .orElse(null);

                if (permission == null) {
                    Permission newPerm = new Permission();
                    newPerm.setTitle(permissionTitle);
                    permission = permissionRepository.save(newPerm);
                }

                savedPermissions.add(permission);
            }

            // 2. Create SuperAdmin role if not exists
            Role superAdminRole = roleRepository.findAll()
                    .stream()
                    .filter(r -> r.getTitle().equals("SuperAdmin"))
                    .findFirst()
                    .orElse(null);

            if (superAdminRole == null) {
                Role role = new Role();
                role.setTitle("SuperAdmin");
                superAdminRole = roleRepository.save(role);
            }

            // 3. Assign all permissions to SuperAdmin role
            Role adminRole = superAdminRole;
            for (Permission perm : savedPermissions) {
                boolean alreadyAssigned = rolePermissionRepository.findAll()
                        .stream()
                        .anyMatch(rp -> rp.getRole().getId() == adminRole.getId() &&
                                rp.getPermission().getId() == perm.getId());
                if (!alreadyAssigned) {
                    RolePermission rp = new RolePermission();
                    rp.setRole(superAdminRole);
                    rp.setPermission(perm);
                    rolePermissionRepository.save(rp);
                }
            }

            // 4. Create default Super Admin user if not exists
            String username = "admin";

            boolean userExists = userRepository.findAll()
                    .stream()
                    .anyMatch(u -> u.getUsername().equals(username));

            if (!userExists) {
                User user = new User();
                user.setFirst_name("Super");
                user.setLast_name("Admin");
                user.setUsername(username);

                // Hash the password using BCrypt
                String hashedPassword = BCrypt.hashpw("admin", BCrypt.gensalt());
                user.setPassword(hashedPassword);

                // Save user first
                User savedUser = userRepository.save(user);

                // Assign role to user (assuming many-to-many)
                savedUser.setRole(superAdminRole);
                userRepository.save(savedUser);
            }
        }


}
