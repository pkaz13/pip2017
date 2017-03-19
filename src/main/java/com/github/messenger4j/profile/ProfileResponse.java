package com.github.messenger4j.profile;

import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_RESULT;

import com.github.messenger4j.exceptions.MessengerIOException;
import com.google.gson.JsonObject;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Created by Rafal Lebioda on 18.03.2017.
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ProfileResponse {

    private final String result;

    public static ProfileResponse fromJson(JsonObject jsonObject) throws MessengerIOException {
        final String result = getPropertyAsString(jsonObject, PROP_RESULT);
        return new ProfileResponse(result);
    }

}
