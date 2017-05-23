package pl.hycom.pip.messanger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.hycom.pip.messanger.model.Keyword;
import pl.hycom.pip.messanger.model.Product;
import pl.hycom.pip.messanger.model.User;
import pl.hycom.pip.messanger.repository.ProductRepository;
import pl.hycom.pip.messanger.repository.UserRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Monia on 2017-05-20.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Log4j2
public class UserService {
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
        User userByEmail = userRepository.findByEmail(user.getEmail());
        if(userByEmail == null) {
            userToUpdate.setFirstname(user.getFirstname());
            userToUpdate.setLastname(user.getLastname());
            userToUpdate.setEmail(user.getEmail());
            return userRepository.save(userToUpdate);
        } else {
            log.info("User with email: " + user.getEmail() + " exists");
        }
        return userToUpdate;
    }

    public void deleteUser(Integer id) {
        log.info("Deleting user[" + id + "]");

        userRepository.delete(id);
    }

    public User findUserByEmail(String email) {
        log.info("findUserByEmail method from UserService invoked");
        return userRepository.findByEmail(email);
    }
}
