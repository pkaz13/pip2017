package com.github.messenger4j.profile;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by Rafal Lebioda on 18.03.2017.
 */
@Data
@NoArgsConstructor
@ToString
public class Greeting {

    private String text;
    private String locale;

    public Greeting(String greeting) {
        locale = "default";
        text = greeting;
    }

}
