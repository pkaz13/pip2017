package com.github.messenger4j.profile;

import com.github.messenger4j.exceptions.MessengerIOException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rafal Lebioda on 18.03.2017.
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class GreetingsProfileResponse extends ProfileResponse {

    private final List<Greeting> greetings;

    public GreetingsProfileResponse(String result) {
        super(result);
        greetings = Collections.emptyList();
    }

    public GreetingsProfileResponse(String result, Greeting[] greetingsArray) {
        super(result);
        greetings = Arrays.asList(greetingsArray);
    }

    public static GreetingsProfileResponse fromJson(JsonObject jsonObject) throws MessengerIOException {
        JsonArray dataArray = getArray(jsonObject, "data");
        JsonArray greetingsJsonArray;
        if (isJsonArrayEmpty(dataArray) || isJsonArrayEmpty(greetingsJsonArray = getGreetingsJsonArray(dataArray))) {
            return new GreetingsProfileResponse(jsonObject.toString());
        } else {
            Greeting[] greetingsArray = new Gson().fromJson(greetingsJsonArray, Greeting[].class);
            return new GreetingsProfileResponse(jsonObject.toString(), greetingsArray);
        }
    }

    private static JsonArray getArray(JsonObject jsonObject, String label) {
        return jsonObject.getAsJsonArray(label);
    }

    private static JsonArray getGreetingsJsonArray(JsonArray dataArray) {
        return getArray(dataArray.get(0).getAsJsonObject(), "greetings");
    }

    private static boolean isJsonArrayEmpty(JsonArray jsonArray) {
        return jsonArray == null || jsonArray.size() == 0;
    }

}
