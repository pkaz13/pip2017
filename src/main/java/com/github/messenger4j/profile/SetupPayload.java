package com.github.messenger4j.profile;

import lombok.Data;

/**
 * Created by Rafal Lebioda on 18.03.2017.
 */
@Data
final class SetupPayload {

    private  Greeting [] greeting;

    static SetupPayload.Builder newBuilder() {
        return new SetupPayload.Builder();
    }

    private SetupPayload(SetupPayload.Builder builder) {
        greeting=new Greeting[1];
        greeting[0] = builder.greeting;
    }

    static final class Builder {
        private Greeting greeting ;

        public SetupPayload.Builder greeting(String greeting) {
            this.greeting = new Greeting(greeting);
            return this;
        }

            public SetupPayload build() {
                return new SetupPayload(this);
            }



    }





}
