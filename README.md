# Book Inventory Backend

This repository contains the backend implementation of the Book Inventory System designed to allow users to manage books with distinct roles for Admin and User. The backend is developed using Java Spring Boot and MongoDB, providing robust support for JWT-based authentication, role-based access control, and CRUD operations for managing book entries.

The Book Inventory System Backend provides an API to manage books in an inventory. The system supports user authentication and role-based authorization for accessing certain resources. The backend implements scalable architecture, enabling easy addition of more roles and complex permissions in the future. This backend system is built using Spring Boot, with JWT-based authentication for secure login, role management, and API access. It uses MySQL for storing user data, roles, and permissions.

## Features

1. **User Registration and Login**: The backend exposes endpoints to register new users and authenticate them using JWT.
2. **Role-based Authentication**: Roles like ADMIN and USER are supported, each with different permissions.
3. **Authorization**: Based on the user's role, the backend enforces access control to endpoints (e.g., only users with the `MANAGE_BOOKS` permission can add, update, or delete books).
4. **Scalability**: The architecture is designed to scale, making it easy to introduce new roles or permissions in the future.

## Architecture

### Role-Based Authentication

The backend implements role-based access control (RBAC). Roles are stored in the database, and each role is associated with specific permissions. The system currently supports two roles:

- **ADMIN**: This role has full access, including managing books (`MANAGE_BOOKS` permission) and viewing books (`VIEW_BOOKS` permission).
- **USER**: This role can only view books and is restricted from managing them.

### Future Scalability: Roles and Permissions

The system is designed to easily scale and introduce new roles or permissions in the future. For example, additional roles like `MANAGER` or `SUPER_ADMIN` can be added along with their respective permissions (e.g., `EDIT_USER`, `VIEW_STATS`). The current structure allows for easy expansion, ensuring that new roles can be integrated with minimal changes to the codebase.

The `DataInitializer` class in the code ensures that roles and permissions are automatically seeded in the database upon startup, which is a scalable approach for maintaining consistency in our database across different environments.

## Security Configuration

- **JWT Authentication**: The backend uses JWT tokens for authenticating users. Upon successful login, a JWT token is generated and sent back to the client. This token must be included in the Authorization header for subsequent API requests.
- **Custom Authentication and Access Denied Handlers**: The system includes custom handlers to manage authentication errors and access denials. This ensures that unauthorized users receive meaningful error messages and are redirected accordingly.

### Example Code for Role Management:

The following code snippet demonstrates how roles and permissions are initialized:

```java
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

        // Create roles and assign permissions
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
