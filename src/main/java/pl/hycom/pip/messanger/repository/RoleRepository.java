package pl.hycom.pip.messanger.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.hycom.pip.messanger.repository.model.Role;

import java.util.Optional;

/**
 * Created by Maciek on 2017-05-27.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByAuthorityIgnoreCase(String authority);
}
