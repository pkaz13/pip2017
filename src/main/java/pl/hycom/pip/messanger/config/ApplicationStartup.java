package pl.hycom.pip.messanger.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.hycom.pip.messanger.repository.model.Role;
import pl.hycom.pip.messanger.repository.model.User;
import pl.hycom.pip.messanger.service.RoleService;
import pl.hycom.pip.messanger.service.UserService;

import java.util.Collections;
import java.util.Optional;

/**
 * Created by Maciek on 2017-05-27.
 */
@Component
@Slf4j
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    // todo zmienic hardocodowanie rol
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("onApplicationEvent method invoked");

        initializeRole("ROLE_ADMIN");
        initializeRole("ROLE_USER");

        if (userService.findAllUsers().isEmpty()) {
            initializeAdminUser();
        }
    }

    private void initializeAdminUser() {
        log.info("initializeAdminUser method invoked");

        User user = new User("admin", "admin", "admin@example.com", "admin", "+48923456783");
        Optional<Role> role = roleService.findRoleByName("ROLE_ADMIN");
        role.ifPresent(r -> {
            user.setRoles(Collections.singletonList(r));
            userService.addUser(user);
        });

        log.warn("ApplicationStartup.initializeAdminUser() - Initializing admin failed!!");
    }

    private void initializeRole(final String roleName) {
        log.info("initializeRole method invoked for role " + roleName);

        Optional<Role> role = roleService.findRoleByName(roleName);
        if (!role.isPresent()) {
            Role newRole = new Role(roleName);
            roleService.addRole(newRole);
        }
    }
}
