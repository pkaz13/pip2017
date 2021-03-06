package pl.hycom.pip.messanger.repository;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.hycom.pip.messanger.repository.model.PasswordResetToken;

/**
 * Created by Piotr on 27.05.2017.
 */
@Repository
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Integer> {

    PasswordResetToken findByToken(String token);

    @Transactional
    Long deleteByExpiryDateLessThan(LocalDateTime date);

}
