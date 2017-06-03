package pl.hycom.pip.messanger.service;

import java.util.UUID;

/**
 * Created by Piotr on 21.05.2017.
 */
public class ResetService {

    public String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
