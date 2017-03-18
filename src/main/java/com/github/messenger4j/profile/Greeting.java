package com.github.messenger4j.profile;

import lombok.Data;

/**
 * Created by Rafal Lebioda on 18.03.2017.
 */
@Data
final class Greeting {
    private  String text;
    private  String locale;

    Greeting(String greeting) {
        this.locale="default";
        text = greeting;
    }

    public Greeting()
    {

    }

}
