package pl.hycom.pip.messanger.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by maciek on 06.03.17.
 */

@Service
@Data
public class ConfigService {

    @Value("${my.config.property.pageAccessToken}")
    private String pageAccessToken;

    @Value("${my.config.property.appSecret}")
    private String appSecret;

    @Value("${my.config.property.verifyToken}")
    private String verifyToken;
}
