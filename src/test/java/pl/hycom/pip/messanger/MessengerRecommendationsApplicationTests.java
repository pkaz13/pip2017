package pl.hycom.pip.messanger;

import com.github.messenger4j.common.MessengerHttpClient;
import com.github.messenger4j.receive.MessengerReceiveClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import pl.hycom.pip.messanger.config.MessengerConfigurationTest;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"dev"})
@ContextConfiguration(classes = { MessengerConfigurationTest.class })
public class MessengerRecommendationsApplicationTests {

    @Autowired
    private MessengerReceiveClient messengerReceiveClient;
    @Autowired
    private MessengerHttpClient mockHttpClient;

    @Test
    public void shouldHandleHelloWorldMessageEvent() throws Exception {
        // given
        final String payload = "{\n" +
                "    \"object\": \"page\",\n" +
                "    \"entry\": [{\n" +
                "        \"id\": \"PAGE_ID\",\n" +
                "        \"time\": 1458692752478,\n" +
                "        \"messaging\": [{\n" +
                "            \"sender\": {\n" +
                "                \"id\": \"USER_ID\"\n" +
                "            },\n" +
                "            \"recipient\": {\n" +
                "                \"id\": \"PAGE_ID\"\n" +
                "            },\n" +
                "            \"timestamp\": 1458692752478,\n" +
                "            \"message\": {\n" +
                "                \"mid\": \"mid.1457764197618:41d102a3e1ae206a38\",\n" +
                "                \"text\": \"hello, text message world!\"\n" +
                "            }\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

        messengerReceiveClient.processCallbackPayload(payload);

        // then
        // TODO: dopisac test jak bedzie dzialac juz pobieranie produktow na podstawie tekstu uzytkownika
        // final String expectedJsonBody = "{\"recipient\":{\"id\":\"USER_ID\"}," + "\"message\":{\"text\":\"Hello World\"}}";
        // verify(mockHttpClient).execute(eq(POST), endsWith("X"), eq(expectedJsonBody));

    }

}
