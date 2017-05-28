package pl.hycom.pip.messanger.util;

import pl.hycom.pip.messanger.exception.PasswordEncryptionException;

/**
 * Created by Monia on 2017-05-28.
 */
public interface PasswordEncryptor {
    String encryptPassword(String password) throws PasswordEncryptionException;
}
