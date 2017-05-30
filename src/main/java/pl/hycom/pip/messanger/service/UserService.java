package pl.hycom.pip.messanger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.hycom.pip.messanger.model.PasswordResetToken;
import pl.hycom.pip.messanger.model.User;
import pl.hycom.pip.messanger.repository.PasswordResetTokenRepository;
import pl.hycom.pip.messanger.repository.UserRepository;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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
    private final PasswordResetTokenRepository tokenRepository;

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

    public String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
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

    public boolean validatePasswordResetToken(String token) {
        log.info("validatePasswordResetToken method invoke");
        if (StringUtils.isEmpty(token)) {
            return false;
        }

        PasswordResetToken resetToken = tokenRepository.findByToken(token);

        if(resetToken == null) {
            return  false;
        }

        Date currentDate = new Date();
        if(currentDate.compareTo(resetToken.getExpiryDate()) > 0) {
            return false;
        }

        User user = resetToken.getUser();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
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

    public PasswordResetToken getTokenByToken(String token)
    {
        return tokenRepository.findByToken(token);
    }
}
