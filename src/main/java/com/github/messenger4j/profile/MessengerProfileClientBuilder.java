package com.github.messenger4j.profile;

import com.github.messenger4j.common.MessengerHttpClient;
import com.github.messenger4j.internal.PreConditions;

/**
 * Created by Rafal Lebioda on 18.03.2017.
 */
public final class MessengerProfileClientBuilder {

    final String pageAccessToken;
    MessengerHttpClient httpClient;

    public MessengerProfileClientBuilder(String pageAccessToken) {
        PreConditions.notNullOrBlank(pageAccessToken, "pageAccessToken");
        this.pageAccessToken = pageAccessToken;
    }

    public MessengerProfileClientBuilder httpClient(MessengerHttpClient messengerHttpClient) {
        this.httpClient = messengerHttpClient;
        return this;
    }

    public  MessengerProfileClient build() {
        return new  MessengerProfileClientImpl(this);
    }
}
