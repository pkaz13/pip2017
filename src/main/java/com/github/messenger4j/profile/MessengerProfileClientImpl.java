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

/**
 * Created by rafal on 18.03.2017.
 */
@Log4j2
final class MessengerProfileClientImpl extends MessengerRestClientAbstract<ProfilePayload, ProfileResponse>
        implements MessengerProfileClient {

    private static final String URL="https://graph.facebook.com/v2.6/me/messenger_profile";
    private static final String GET_URL_PARAMETERS = "?fields=greeting&access_token=%s";
    private static final String POST_AND_DELETE_URL_PARAMETERS = "?access_token=%s";

    private final String pageAccessToken;

    private  String requestUrl;

    MessengerProfileClientImpl(MessengerProfileClientBuilder builder) {
        super(builder.httpClient);
        pageAccessToken= builder.pageAccessToken;
        this.requestUrl = URL;
        log.debug("{} initialized successfully.", MessengerProfileClientImpl.class.getSimpleName());
    }


    @Override
    public ProfileResponse setupWelcomeMessage(String greeting) throws MessengerApiException, MessengerIOException {
        final ProfilePayload payload = ProfilePayload.newBuilder()
                .greeting(greeting)
                .build();

        String fullUrl=requestUrl+String.format(POST_AND_DELETE_URL_PARAMETERS,pageAccessToken);
        return sendPayload(POST, payload,fullUrl);
    }

    @Override
    public ProfileResponse removeWelcomeMessage() throws MessengerApiException, MessengerIOException {
        final ProfilePayload payload = ProfilePayload.newBuilder()
                .build();

        String fullUrl=requestUrl+String.format(POST_AND_DELETE_URL_PARAMETERS,pageAccessToken);
        return sendPayload(DELETE, payload,fullUrl);
    }

    @Override
    public ProfileResponse getWelcomeMessage() throws MessengerApiException, MessengerIOException {
        final ProfilePayload payload = ProfilePayload.newBuilder()
                .build();

        String fullUrl=requestUrl+String.format(GET_URL_PARAMETERS,pageAccessToken);
        return sendPayload(GET, payload,fullUrl);
    }

    @Override
    protected ProfileResponse responseFromJson(JsonObject responseJsonObject) {
        return ProfileResponse.fromJson(responseJsonObject);
    }

    private ProfileResponse sendPayload(HttpMethod httpMethod, ProfilePayload payload,String url)
            throws MessengerApiException, MessengerIOException {

        return doRequest(httpMethod, url, payload);
    }
}
