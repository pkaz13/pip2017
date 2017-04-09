package pl.hycom.pip.messanger.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * Created by rafal on 13.03.2017.
 */
@Data
@NoArgsConstructor
public class Greeting {

    @Size(min = 2, max = 160, message = "{greeting.text.size}")
    private String text;
    private String locale = "";

    public Greeting(com.github.messenger4j.profile.Greeting profileGreeting) {
        setText(profileGreeting.getText());
        setLocale(profileGreeting.getLocale());
    }
}
