package com.github.messenger4j.profile;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Rafal Lebioda on 18.03.2017.
 * Supported locales: https://developers.facebook.com/docs/messenger-platform/messenger-profile/supported-locales
 */
@Data
@NoArgsConstructor
public class Greeting {

    private String text;
    private String locale;

    public Greeting(String text) {
        locale = "default";
        this.text = text;
    }

    public Greeting(String text, String locale) {
        this.text = text;
        this.locale = locale;
    }
}
