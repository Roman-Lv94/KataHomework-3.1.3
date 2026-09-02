package ru.kata.spring.boot_security.demo.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.Collections;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;

    public DataInitializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public void run(String... args) {

        Role roleAdmin = roleService.getRoleByName("ROLE_ADMIN");
        if (roleAdmin == null) {
            roleAdmin = new Role("ROLE_ADMIN");
            roleService.saveRole(roleAdmin);
        }

        Role roleUser = roleService.getRoleByName("ROLE_USER");
        if (roleUser == null) {
            roleUser = new Role("ROLE_USER");
            roleService.saveRole(roleUser);
        }

        if (userService.getUserByEmail("admin@mail.ru") == null) {

            User admin = new User();
            admin.setFirstName("AdminFirstName");
            admin.setLastName("AdminLastName");
            admin.setAge(31);
            admin.setEmail("admin@mail.ru");
            admin.setPassword("admin");

            // Изменение: администратору теперь присваиваются обе роли
            admin.setRoles(Set.of(roleAdmin, roleUser));

            userService.saveUser(admin);
        }

        if (userService.getUserByEmail("user@mail.ru") == null) {

            User user = new User();
            user.setFirstName("UserFirstName");
            user.setLastName("UserLastName");
            user.setAge(30);
            user.setEmail("user@mail.ru");
            user.setPassword("user");
            user.setRoles(Collections.singleton(roleUser));

            userService.saveUser(user);
        }
    }
}