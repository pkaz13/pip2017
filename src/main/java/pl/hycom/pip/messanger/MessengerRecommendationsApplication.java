package pl.hycom.pip.messanger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"pl.hycom"})
public class MessengerRecommendationsApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessengerRecommendationsApplication.class, args);
    }

}
