package pl.hycom.pip.messanger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.messenger4j.MessengerPlatform;
import com.github.messenger4j.receive.MessengerReceiveClient;
import com.github.messenger4j.send.MessengerSendClient;

import pl.hycom.pip.messanger.handler.MessengerTextMessageHandler;

/**
 * Created by patry on 07/03/2017.
 */

@Configuration
public class MessengerConfiguration {

	@Value("${messenger.pageAccessToken}") private String pageAccessToken;
	@Value("${messenger.appSecret}") private String appSecret;
	@Value("${messenger.verifyToken}") private String verifyToken;

	@Bean
	public MessengerTextMessageHandler messengerTextMessageHandler() {
		return new MessengerTextMessageHandler(sendClient());
	}

	@Bean
	public MessengerReceiveClient receiveClient() {
		return MessengerPlatform.newReceiveClientBuilder(appSecret, verifyToken)
				.onTextMessageEvent(messengerTextMessageHandler())
				.build();
	}

	@Bean
	public MessengerSendClient sendClient() {
		return MessengerPlatform.newSendClientBuilder(pageAccessToken).build();
	}

}
