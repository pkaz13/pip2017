package pl.hycom.pip.messanger.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;
import java.util.MissingResourceException;

/**
 * Created by rafal on 13.03.2017.
 */
@Data
@NoArgsConstructor
public class Greeting {

    private String text;
    private String locale = "";

    public Greeting(com.github.messenger4j.profile.Greeting profileGreeting) {
        setText(profileGreeting.getText());
        setLocale(profileGreeting.getLocale());
    }

    public boolean isValid() {
        String[] locales = locale.split("_");
        return locales.length == 2 && isLocaleValid(new Locale(locales[0], locales[1]));
    }

    private boolean isLocaleValid(Locale locale) {
        try {
            return locale.getISO3Language() != null && locale.getISO3Country() != null;
        } catch (MissingResourceException e) {
            return false;
        }
    }
}
