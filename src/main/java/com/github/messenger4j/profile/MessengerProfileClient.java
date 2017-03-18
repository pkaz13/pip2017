package com.github.messenger4j.profile;

import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;

/**
 * Created by Rafal Lebioda on 18.03.2017.
 */
public interface MessengerProfileClient {

    SetupResponse setupWelcomeMessage(String greeting) throws MessengerApiException, MessengerIOException;

    SetupResponse removeWelcomeMessage() throws MessengerApiException, MessengerIOException;

    SetupResponse getWelcomeMessage() throws MessengerApiException, MessengerIOException;
}
