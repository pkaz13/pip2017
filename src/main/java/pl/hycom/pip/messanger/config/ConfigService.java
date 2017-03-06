package pl.hycom.pip.messanger.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by maciek on 06.03.17.
 */

@Service
@Data
public class ConfigService {
    @Autowired
    private MessengerIntegrationProperties msgIntegrationProperties;
}
