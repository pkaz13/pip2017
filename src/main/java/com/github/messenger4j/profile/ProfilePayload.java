package com.github.messenger4j.profile;

import lombok.Data;

import java.util.List;

/**
 * Created by Rafal Lebioda on 18.03.2017.
 */
@Data
final class ProfilePayload {

    private final String[] fields = new String[]{"greeting"}; // This is needed for deleting greetings. Do not remove unless u handle greetings
    private Greeting[] greeting;

    private ProfilePayload(ProfilePayload.Builder builder) {
        greeting = new Greeting[]{builder.greeting};
    }

    public ProfilePayload(List<Greeting> greetings) {
        this.greeting = greetings.toArray(new Greeting[]{});
    }

    static ProfilePayload.Builder newBuilder() {
        return new ProfilePayload.Builder();
    }

    static final class Builder {
        private Greeting greeting;

        public ProfilePayload.Builder greeting(Greeting greeting) {
            this.greeting = greeting;
            return this;
        }

        public ProfilePayload build() {
            return new ProfilePayload(this);
        }

    }

}
