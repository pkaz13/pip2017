package pl.hycom.pip.messanger.config;

import com.github.messenger4j.MessengerPlatform;
import com.github.messenger4j.receive.MessengerReceiveClient;
import com.github.messenger4j.send.MessengerSendClient;
import com.github.messenger4j.setup.MessengerSetupClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.hycom.pip.messanger.handler.MessengerHelloWorldHandler;

/**
 * Created by patry on 07/03/2017.
 */

@Configuration
public class MessengerConfiguration {

	@Value("${messenger.pageAccessToken}") private String pageAccessToken;
	@Value("${messenger.appSecret}") private String appSecret;
	@Value("${messenger.verifyToken}") private String verifyToken;

	@Bean
	public MessengerHelloWorldHandler messengerHelloWorldHandler() {
		return new MessengerHelloWorldHandler(sendClient());
	}

	@Bean
	public MessengerReceiveClient receiveClient() {
		return MessengerPlatform.newReceiveClientBuilder(appSecret, verifyToken)
				.onTextMessageEvent(messengerHelloWorldHandler())
				.build();
	}

	@Bean
	public MessengerSendClient sendClient() {
		return MessengerPlatform.newSendClientBuilder(pageAccessToken).build();
	}

	@Bean
	public MessengerSetupClient setupClient() {return MessengerPlatform.newSetupClientBuilder(pageAccessToken).build();}

}
