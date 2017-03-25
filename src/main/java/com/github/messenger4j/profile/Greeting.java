package com.github.messenger4j.profile;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Rafal Lebioda on 18.03.2017.
 */
@Data
@NoArgsConstructor
public class Greeting {

    private String text;
    private String locale;

    public Greeting(String greeting) {
        locale = "pl_PL";
        text = greeting;
    }

    public Greeting(String text, String locale) {
        this.text = text;
        this.locale = locale;
    }
}
