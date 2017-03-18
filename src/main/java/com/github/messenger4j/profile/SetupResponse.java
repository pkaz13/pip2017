package com.github.messenger4j.profile;

import com.google.gson.JsonObject;
import lombok.Data;

import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_RESULT;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;

/**
 * Created by Rafal Lebioda on 18.03.2017.
 */
@Data
public final class SetupResponse {


    private final String result;

    public static SetupResponse fromJson(JsonObject jsonObject) {
        final String result = getPropertyAsString(jsonObject, PROP_RESULT);
        return new SetupResponse(result);
    }

    private SetupResponse(String result) {
        this.result = result;
    }
}
