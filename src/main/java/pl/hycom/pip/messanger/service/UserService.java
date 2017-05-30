package pl.hycom.pip.messanger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.hycom.pip.messanger.controller.model.UserDTO;
import pl.hycom.pip.messanger.model.PasswordResetToken;
import pl.hycom.pip.messanger.repository.PasswordResetTokenRepository;
import pl.hycom.pip.messanger.repository.RoleRepository;
import pl.hycom.pip.messanger.repository.UserRepository;
import pl.hycom.pip.messanger.repository.model.Role;
import pl.hycom.pip.messanger.repository.model.User;

import javax.inject.Inject;
import java.util.*;
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
    private final RoleRepository roleRepository;
    private final PasswordResetTokenRepository tokenRepository;
    @Autowired
    private MapperFacade orikaMapper;

    public List<UserDTO> findAllUsers() {
        log.info("Searching all users");

        return orikaMapper.mapAsList(StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList()), UserDTO.class);
    }

    public UserDTO addOrUpdateUser(UserDTO user) {
        User userToUpdateOrAdd = orikaMapper.map(user, User.class);
        if (user.getId() != null && user.getId() != 0) {
            return orikaMapper.map(updateUser(userToUpdateOrAdd), UserDTO.class);
        } else {
            return orikaMapper.map(addUser(userToUpdateOrAdd), UserDTO.class);
        }
    }

    public User addUser(User user) {
        log.info("Adding user: " + user);
        setUserRoleIfNoneGranted(user);
        return userRepository.save(user);
    }

    private void setUserRoleIfNoneGranted(User user) {
        log.info("setUserRoleIfNoneGranted method invoked for user: " + user);
        if (CollectionUtils.isEmpty(user.getAuthorities())) {
            roleRepository.findByAuthorityIgnoreCase(Role.RoleName.ROLE_USER.name())
                    .ifPresent(role -> user.setRoles(Collections.singleton(role)));
        }
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
            userToUpdate.setPhoneNumber(user.getPhoneNumber());
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

    public String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public User findUserByEmail(String email) {
        log.info("findUserByEmail method from UserService invoked");
        return userRepository.findByEmail(email).get();
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        int urlActivityLength = 30;
        Date targetDate = new Date();
        targetDate = DateUtils.addMinutes(targetDate, urlActivityLength);
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(token);
        resetToken.setExpiryDate(targetDate);
        tokenRepository.save(resetToken);
    }

    public boolean validatePasswordResetToken(String token, String email) {
        log.info("validatePasswordResetToken method invoke");
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        User user = resetToken.getUser();
        if (resetToken == null) {
            return false;
        }

        if (!user.getEmail().equals(email)) {
            return false;
        }

        Date currentDate = new Date();
        if (currentDate.compareTo(resetToken.getExpiryDate()) > 0) {
            return false;
        }
        return true;
    }

    public void changePassword(User user, String password) {
        log.info("changePassword method invoked");
        User userToUpdate = userRepository.findOne(user.getId());
        userToUpdate.setPassword(password);
        userRepository.save(userToUpdate);
    }

    public User getUserByToken(String token) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        return resetToken.getUser();
    }

    public PasswordResetToken getTokenByToken(String token) {
        return tokenRepository.findByToken(token);
    }
}
