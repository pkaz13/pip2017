package com.github.messenger4j;

import com.github.messenger4j.profile.MessengerProfileClientBuilder;
import com.github.messenger4j.receive.MessengerReceiveClientBuilder;
import com.github.messenger4j.send.MessengerSendClientBuilder;
import com.github.messenger4j.setup.MessengerSetupClientBuilder;
import com.github.messenger4j.user.UserProfileClientBuilder;

/**
 * Wrapper powstal w celu umozliwienia rozszerzalnosci klasy MessengerPlatform, ktora jest finalna.
 *
 * @author Hubert Pruszynski
 *
 */
public class MessengerPlatformWrapper {

    public static MessengerReceiveClientBuilder newReceiveClientBuilder(String appSecret, String verifyToken) {
        return MessengerPlatform.newReceiveClientBuilder(appSecret, verifyToken);
    }

    public static MessengerSendClientBuilder newSendClientBuilder(String pageAccessToken) {
        return MessengerPlatform.newSendClientBuilder(pageAccessToken);
    }

    public static MessengerSetupClientBuilder newSetupClientBuilder(String pageAccessToken) {
        return MessengerPlatform.newSetupClientBuilder(pageAccessToken);
    }

    public static UserProfileClientBuilder newUserProfileClientBuilder(String pageAccessToken) {
        return MessengerPlatform.newUserProfileClientBuilder(pageAccessToken);
    }

    public static MessengerProfileClientBuilder newProfileClientBuilder(String pageAccessToken) {
        return new MessengerProfileClientBuilder(pageAccessToken);
    }
}
