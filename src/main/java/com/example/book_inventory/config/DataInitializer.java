package com.example.book_inventory.config;

import com.example.book_inventory.model.Permission;
import com.example.book_inventory.model.Role;
import com.example.book_inventory.repository.PermissionRepository;
import com.example.book_inventory.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
//To create and store initial roles (ADMIN, USER) and permissions (MANAGE_BOOKS, VIEW_BOOKS) in the database on application startup.
    @Bean
    public CommandLineRunner initRolesAndPermissions(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        return args -> {
            // Create permissions
            Permission manageBooks = new Permission();
            manageBooks.setName("MANAGE_BOOKS");
            permissionRepository.save(manageBooks);

            Permission viewBooks = new Permission();
            viewBooks.setName("VIEW_BOOKS");
            permissionRepository.save(viewBooks);

            // Create roles
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            adminRole.getPermissions().add(manageBooks);
            adminRole.getPermissions().add(viewBooks);
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName("USER");
            userRole.getPermissions().add(viewBooks);
            roleRepository.save(userRole);
        };
    }
}
