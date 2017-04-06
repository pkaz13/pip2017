package pl.hycom.pip.messanger.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import pl.hycom.pip.messanger.service.GreetingService;

/**
 * Created by marcinus on 04.04.17.
 */
public class GreetingTest {

    @Autowired
    private GreetingService greetingService;

    @Test
    public void isValid_pl_PL() throws Exception {
        Greeting greeting = new Greeting();
        greeting.setLocale("pl_PL");

        boolean valid = greetingService.isValidLocale(greeting.getLocale());

        Assertions.assertThat(valid).isTrue();
    }

    @Test
    public void isInvalid_pl() throws Exception {
        Greeting greeting = new Greeting();
        greeting.setLocale("pl");

        boolean valid = greetingService.isValidLocale(greeting.getLocale());

        Assertions.assertThat(valid).isFalse();
    }

    @Test
    public void isValid_en_US() throws Exception {
        Greeting greeting = new Greeting();
        greeting.setLocale("en_US");

        boolean valid = greetingService.isValidLocale(greeting.getLocale());

        Assertions.assertThat(valid).isTrue();
    }

    @Test
    public void isInvalid_random() throws Exception {
        Greeting greeting = new Greeting();
        greeting.setLocale("adadads");

        boolean valid = greetingService.isValidLocale(greeting.getLocale());

        Assertions.assertThat(valid).isFalse();
    }

    @Test
    public void isInvalid_random_random() throws Exception {
        Greeting greeting = new Greeting();
        greeting.setLocale("sdvkj_skdksdsf");

        boolean valid = greetingService.isValidLocale(greeting.getLocale());

        Assertions.assertThat(valid).isFalse();
    }

    @Test
    public void isInvalid_pl_random() throws Exception {
        Greeting greeting = new Greeting();
        greeting.setLocale("pl_skdksdsf");

        boolean valid = greetingService.isValidLocale(greeting.getLocale());

        Assertions.assertThat(valid).isFalse();
    }

    @Test
    public void isInvalid_random_PL() throws Exception {
        Greeting greeting = new Greeting();
        greeting.setLocale("sdvkj_PL");

        boolean valid = greetingService.isValidLocale(greeting.getLocale());

        Assertions.assertThat(valid).isFalse();
    }

}
