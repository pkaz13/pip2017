package pl.hycom.pip.messanger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.hycom.pip.messanger.model.User;
import pl.hycom.pip.messanger.repository.UserRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Monia on 2017-05-20.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Log4j2
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public List<User> findAllUsers() {
        log.info("Searching all users");

        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public void addOrUpdateUser(User user) {
        if (user.getId() != null && user.getId() != 0) {
            updateUser(user);
        } else {
            addUser(user);
        }
    }

    public User addUser(User user) {
        log.info("Adding user: " + user);

        return userRepository.save(user);
    }

    public User updateUser(User user) {
        log.info("Updating user: " + user);
        User userToUpdate = userRepository.findOne(user.getId());
        Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());
        if (userByEmail.isPresent()) {
            log.info("User with email: " + user.getEmail() + " exists");
        } else {
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setEmail(user.getEmail());
            return userRepository.save(userToUpdate);
        }
        return userToUpdate;
    }

    public void deleteUser(Integer id) {
        log.info("Deleting user[" + id + "]");

        userRepository.delete(id);
    }

    @Override
    public User loadUserByUsername(String email) {
        log.info("loadUserByUsername method from UserService invoked");
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email=%s was not found", email)));
    }
}
