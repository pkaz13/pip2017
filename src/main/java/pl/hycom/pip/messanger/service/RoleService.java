package pl.hycom.pip.messanger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.hycom.pip.messanger.repository.RoleRepository;
import pl.hycom.pip.messanger.repository.model.Role;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by Maciek on 2017-05-27.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Log4j2
public class RoleService {

    private final RoleRepository roleRepository;

    public Role addRole(Role role) {
        log.info("Adding role " + role);
        return roleRepository.save(role);
    }

    public Optional<Role> findRoleByName(final String roleName) {
        log.info("Searching for role: " + roleName);
        return roleRepository.findByAuthorityIgnoreCase(roleName);
    }
}
