package com.github.messenger4j.profile;

import com.github.messenger4j.common.MessengerRestClientAbstract;
import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.google.gson.JsonObject;
import static com.github.messenger4j.common.MessengerHttpClient.HttpMethod.DELETE;
import static com.github.messenger4j.common.MessengerHttpClient.HttpMethod.POST;
import static com.github.messenger4j.common.MessengerHttpClient.HttpMethod.GET;

import com.github.messenger4j.common.MessengerHttpClient.HttpMethod;
import lombok.extern.log4j.Log4j2;

import java.util.logging.Logger;

/**
 * Created by rafal on 18.03.2017.
 */
@Log4j2
final class MessengerProfileClientImpl extends MessengerRestClientAbstract<SetupPayload, SetupResponse>
        implements MessengerProfileClient {

    private static final String GET_URL = "https://graph.facebook.com/v2.6/me/messenger_profile?fields=greeting&access_token=%s";

    private static final String POST_AND_DELETE_URL = "https://graph.facebook.com/v2.6/me/messenger_profile?access_token=%s";

    private final String pageAccessToken;

    private  String requestUrl;

    MessengerProfileClientImpl(MessengerProfileClientBuilder builder) {
        super(builder.httpClient);
        pageAccessToken= builder.pageAccessToken;
        log.debug("{} initialized successfully.", MessengerProfileClientImpl.class.getSimpleName());
    }


    @Override
    public SetupResponse setupWelcomeMessage(String greeting) throws MessengerApiException, MessengerIOException {
        final SetupPayload payload = SetupPayload.newBuilder()
                .greeting(greeting)
                .build();
        this.requestUrl = String.format(POST_AND_DELETE_URL, pageAccessToken);

        return sendPayload(POST, payload);
    }

    @Override
    public SetupResponse removeWelcomeMessage() throws MessengerApiException, MessengerIOException {
        final SetupPayload payload = SetupPayload.newBuilder()
                .build();
        this.requestUrl = String.format(POST_AND_DELETE_URL, pageAccessToken);

        return sendPayload(DELETE, payload);
    }

    @Override
    public SetupResponse getWelcomeMessage() throws MessengerApiException, MessengerIOException {
        final SetupPayload payload = SetupPayload.newBuilder()
                .build();

        this.requestUrl = String.format(GET_URL, pageAccessToken);
        return sendPayload(GET, payload);
    }

    @Override
    protected SetupResponse responseFromJson(JsonObject responseJsonObject) {
        return SetupResponse.fromJson(responseJsonObject);
    }

    private SetupResponse sendPayload(HttpMethod httpMethod, SetupPayload payload)
            throws MessengerApiException, MessengerIOException {

        return doRequest(httpMethod, this.requestUrl, payload);
    }
}
