package pl.hycom.pip.messanger.util;

import org.apache.commons.codec.binary.Hex;
import pl.hycom.pip.messanger.exception.PasswordEncryptionException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Monia on 2017-05-28.
 */
public class SHA256PasswordEncryptor implements PasswordEncryptor {
    private static final String SHA_256 = "SHA-256";

    /**
     * Hashowanie hasła podanego w postaci jawnej przy użyciu algorytmu SHA-256.
     *
     * @param password hasło w postaci jawnej
     * @return hasło po wykonaniu hashowania (obiekt typu String)
     * @throws PasswordEncryptionException wyjątek szyfrowania (rzucany gdy nie istnieje odpowiedni algorytm)
     */
    @Override
    public String encryptPassword(String password) throws PasswordEncryptionException {

        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(SHA_256);
        } catch (NoSuchAlgorithmException ex) {
            throw new PasswordEncryptionException(ex.getMessage());
        }
        byte[] encrypted = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(encrypted);
    }
}
