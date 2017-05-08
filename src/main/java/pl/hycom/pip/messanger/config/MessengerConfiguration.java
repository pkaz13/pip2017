package pl.hycom.pip.messanger.config;

import com.github.messenger4j.MessengerPlatformWrapper;
import com.github.messenger4j.profile.MessengerProfileClient;
import com.github.messenger4j.receive.MessengerReceiveClient;
import com.github.messenger4j.send.MessengerSendClient;
import com.github.messenger4j.setup.MessengerSetupClient;
import com.github.messenger4j.user.UserProfileClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.hycom.pip.messanger.handler.PipelineMessageHandler;
import pl.hycom.pip.messanger.pipeline.PipelineManager;

/**
 * Created by patry on 07/03/2017.
 */

@Configuration
public class MessengerConfiguration {

    @Value("${messenger.pageAccessToken}")
    private String pageAccessToken;

    @Value("${messenger.appSecret}")
    private String appSecret;

    @Value("${messenger.verifyToken}")
    private String verifyToken;

    @Value("${messenger.pipeline.filepath}")
    private String pipelineFilepath;

    @Bean
    public PipelineManager pipelineManager() {
        return new PipelineManager(pipelineFilepath);
    }

    @Bean
    public PipelineMessageHandler pipelineMessageHandler() {
        return new PipelineMessageHandler(pipelineManager());
    }

    @Bean
    public MessengerReceiveClient receiveClient() {
        return MessengerPlatformWrapper.newReceiveClientBuilder(appSecret, verifyToken)
                .onTextMessageEvent(pipelineMessageHandler())
                .build();
    }

    @Bean
    public MessengerSendClient sendClient() {
        return MessengerPlatformWrapper.newSendClientBuilder(pageAccessToken).build();
    }

    @Bean
    public MessengerSetupClient setupClient() {
        return MessengerPlatformWrapper.newSetupClientBuilder(pageAccessToken).build();
    }

    @Bean
    public MessengerProfileClient profileClient() {
        return MessengerPlatformWrapper.newProfileClientBuilder(pageAccessToken).build();
    }

    @Bean
    public UserProfileClient userProfileClient() {
        return MessengerPlatformWrapper.newUserProfileClientBuilder(pageAccessToken).build();
    }

}
