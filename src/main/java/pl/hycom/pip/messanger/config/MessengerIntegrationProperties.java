package pl.hycom.pip.messanger.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by maciek on 06.03.17.
 */
@Data
@Service
@ConfigurationProperties("my.config.property")
public class MessengerIntegrationProperties {

//    @Value("${property.pageAccessToken}")
    private String pageAccessToken;

//    @Value("${property.appSecret}")
    private String appSecret;

//    @Value("${property.verifyToken}")
    private String verifyToken;
}
