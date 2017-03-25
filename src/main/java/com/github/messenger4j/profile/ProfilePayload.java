package com.github.messenger4j.profile;

import lombok.Data;

/**
 * Created by Rafal Lebioda on 18.03.2017.
 */
@Data
final class ProfilePayload {

    private Greeting[] greeting;

    private ProfilePayload(ProfilePayload.Builder builder) {
        greeting = new Greeting[1];
        greeting[0] = builder.greeting;
    }

    static ProfilePayload.Builder newBuilder() {
        return new ProfilePayload.Builder();
    }

    static final class Builder {
        private Greeting greeting;

        public ProfilePayload.Builder greeting(String greeting) {
            this.greeting = new Greeting(greeting);
            return this;
        }

        public ProfilePayload build() {
            return new ProfilePayload(this);
        }


    }


}
