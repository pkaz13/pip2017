package pl.hycom.pip.messanger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"pl.hycom"})
public class MessengerRecommendationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessengerRecommendationsApplication.class, args);
	}
}
