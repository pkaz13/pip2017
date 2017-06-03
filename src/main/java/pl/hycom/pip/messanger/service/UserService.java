package pl.hycom.pip.messanger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.hycom.pip.messanger.controller.model.UserDTO;
import pl.hycom.pip.messanger.mail.Message;
import pl.hycom.pip.messanger.model.PasswordResetToken;
import pl.hycom.pip.messanger.repository.PasswordResetTokenRepository;
import pl.hycom.pip.messanger.repository.RoleRepository;
import pl.hycom.pip.messanger.exception.EmailNotUniqueException;
import pl.hycom.pip.messanger.repository.model.User;
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
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setFirstName(user.getLastName());
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
            log.info("Token is invalid");
            return false;
        }

        if (!user.getEmail().equals(email)) {
            log.info("Token is invalid");
            return false;
        }

        Date currentDate = new Date();
        if (currentDate.compareTo(resetToken.getExpiryDate()) > 0) {
            log.info("Token expired");
            return false;
        }
        tokenRepository.delete(resetToken);
        log.info("Token is valid");
        log.info("token " + token + " removed from database");
        return true;
    }

    public void changePassword(User user, String password) {
        User userToUpdate = userRepository.findOne(user.getId());
        userToUpdate.setPassword(password);
        userRepository.save(userToUpdate);
        log.info("User " + user.getUsername() + " changed password for " + password);
    }

    public PasswordResetToken getTokenByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public SimpleMailMessage constructResetTokenEmail(String contextPath, User user, String token) {
        List<String> to = new ArrayList<>();
        to.add(user.getEmail());
        String url = contextPath + "/change/password/token/" + token;
        Message message = new Message("messenger.recommendations2017@gmail.com", to, "Reset password", url);
        return message.constructEmail();
    }
}
