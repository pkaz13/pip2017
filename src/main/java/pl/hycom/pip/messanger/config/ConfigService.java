package pl.hycom.pip.messanger.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by maciek on 21.03.17.
 */

@Data
@Service
public class ConfigService {
    @Value("${messenger.pageAccessToken}")
    private String pageAccessToken;

    @Value("${messenger.appSecret}")
    private String appSecret;

    @Value("${messenger.verifyToken}")
    private String verifyToken;

    @Value("${messenger.recommendation.products-amount:3}")
    private Integer productsAmount;
}
