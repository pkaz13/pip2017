package com.github.messenger4j.profile;

import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;

import java.util.List;

/**
 * Created by Rafal Lebioda on 18.03.2017.
 */
public interface MessengerProfileClient {

    ProfileResponse setupWelcomeMessage(Greeting greeting) throws MessengerApiException, MessengerIOException;

    ProfileResponse setupWelcomeMessages(List<Greeting> greetingList) throws MessengerApiException, MessengerIOException;

    ProfileResponse removeWelcomeMessage() throws MessengerApiException, MessengerIOException;

    GreetingsProfileResponse getWelcomeMessage() throws MessengerApiException, MessengerIOException;
}
