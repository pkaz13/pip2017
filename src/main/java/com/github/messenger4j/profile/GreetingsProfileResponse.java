package com.github.messenger4j.profile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.github.messenger4j.exceptions.MessengerIOException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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

        JsonElement data = jsonObject.getAsJsonArray("data").get(0);
        if (data == null) {
            return new GreetingsProfileResponse(jsonObject.toString());
        }

        JsonArray greetingsJsonArray = data.getAsJsonObject().getAsJsonArray("greeting");
        if (greetingsJsonArray == null) {
            return new GreetingsProfileResponse(jsonObject.toString());
        }

        try {
            Greeting[] greetingsArray = new Gson().fromJson(greetingsJsonArray, Greeting[].class);
            return new GreetingsProfileResponse(jsonObject.toString(), greetingsArray);

        } catch (Exception e) {
            throw new MessengerIOException(e);
        }
    }

}
