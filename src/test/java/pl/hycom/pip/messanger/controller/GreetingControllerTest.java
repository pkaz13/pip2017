package pl.hycom.pip.messanger.controller;

import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.profile.Greeting;
import com.github.messenger4j.profile.GreetingsProfileResponse;
import com.github.messenger4j.profile.MessengerProfileClient;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import pl.hycom.pip.messanger.model.GreetingListWrapper;
import pl.hycom.pip.messanger.service.GreetingService;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author Augustyn on 2017-05-09, HYCOM S.A.
 */
@Log4j2
@RunWith(MockitoJUnitRunner.class)
public class GreetingControllerTest {

    @Mock private MessengerProfileClient profileClient;
    @Mock private GreetingService greetingService;
    @Mock private MessageSource bundleMessageSource;

    @InjectMocks private GreetingController controller;

    private Model model;
    private Greeting greeting;

    @Before
    public void setUp() {
        controller = new GreetingController();
        MockitoAnnotations.initMocks(this);
        model = new ExtendedModelMap();
        greeting = new Greeting("Hello world. Zazółć gęślą jaźń.", "pl_PL");
    }

    @Test
    public void getGreetingsTest() throws MessengerApiException, MessengerIOException {
        //given
        GreetingsProfileResponse resp = new GreetingsProfileResponse("result", new Greeting[]{greeting});
        when(profileClient.getWelcomeMessage()).thenReturn(resp);
        //when
        final String resultView = controller.getGreetings(model);
        //then
        assertThat(resultView).isEqualTo(GreetingController.VIEW_GREETINGS);
        final Map<String, Object> map = model.asMap();
        //greet should be:
        final pl.hycom.pip.messanger.model.Greeting resultGreet = (pl.hycom.pip.messanger.model.Greeting) map.get("greeting");
        assertThat(resultGreet.getText()).isEqualTo(null);
        assertThat(resultGreet.getLocale()).isEqualTo("");
        //greetingWrapper should be:
        final GreetingListWrapper wrapper = (GreetingListWrapper) map.get("greetingListWrapper");
        assertThat(wrapper.getGreetings().size()).isEqualTo(2);
        assertThat(wrapper.getGreetings()).contains(new pl.hycom.pip.messanger.model.Greeting(greeting));
        assertThat(wrapper.getGreetings()).contains(new pl.hycom.pip.messanger.model.Greeting(new Greeting("")));
        //locale list should be:
        final Map<String, String> availableLocale = (Map<String, String>) map.get("availableLocale");
        assertThat(availableLocale.isEmpty()).isTrue();
    }
}
