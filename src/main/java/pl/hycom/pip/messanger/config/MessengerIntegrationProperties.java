package pl.hycom.pip.messanger.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by maciek on 06.03.17.
 */

@Component
@Data
@ConfigurationProperties(prefix = "my.config.property")
public class MessengerIntegrationProperties {
    private String pageAccessToken;
    private String appSecret;
    private String verifyToken;
}
