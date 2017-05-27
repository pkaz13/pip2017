package pl.hycom.pip.messanger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ma.glasnost.orika.MapperFacade;
import org.h2.jdbc.JdbcSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.hycom.pip.messanger.controller.model.UserDTO;
import pl.hycom.pip.messanger.exception.EmailNotUniqueException;
import pl.hycom.pip.messanger.repository.model.User;
import pl.hycom.pip.messanger.repository.UserRepository;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
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

    @Autowired
    private MapperFacade orikaMapper;

    private final UserRepository userRepository;

    public List<UserDTO> findAllUsers() {
        log.info("Searching all users");

        return orikaMapper.mapAsList(StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList()), UserDTO.class);
    }

    public UserDTO addOrUpdateUser(UserDTO user) throws EmailNotUniqueException{
        User userToUpdateOrAdd = orikaMapper.map(user, User.class);
        if (user.getId() != null && user.getId() != 0) {
            return orikaMapper.map(updateUser(userToUpdateOrAdd), UserDTO.class);
        } else {
            return orikaMapper.map(addUser(userToUpdateOrAdd), UserDTO.class);
        }
    }

    public User addUser(User user) throws EmailNotUniqueException {
        log.info("Adding user: " + user);
        user.setEmail(user.getEmail().toLowerCase());
        return trySaveUser(user);
    }

    public User updateUser(User user) throws EmailNotUniqueException{
        log.info("Updating user: " + user);
        User userToUpdate = userRepository.findOne(user.getId());
        userToUpdate.setFirstname(user.getFirstname());
        userToUpdate.setLastname(user.getLastname());
        userToUpdate.setPhoneNumber(user.getPhoneNumber());
        userToUpdate.setEmail(user.getEmail().toLowerCase());
        return trySaveUser(userToUpdate);
    }

    private User trySaveUser(User user) throws EmailNotUniqueException{
        try {
            return userRepository.save(user);

        } catch (DataIntegrityViolationException e) {
            throw new EmailNotUniqueException(e.getCause());
        }
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
