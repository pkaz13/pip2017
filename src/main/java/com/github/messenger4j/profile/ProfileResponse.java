package com.github.messenger4j.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j2;


import java.io.IOException;


/**
 * Created by Rafal Lebioda on 18.03.2017.
 */
@lombok.Data
@Log4j2
public final class ProfileResponse {


    private final String result;

    public static ProfileResponse fromJson(JsonObject jsonObject) {
        ObjectMapper mapper = new ObjectMapper();
        DataProfileResponseModel data=new DataProfileResponseModel();
        try {
            JsonArray array=jsonObject.getAsJsonArray("data");
            JsonElement obj=array.get(0);
            data = mapper.readValue(obj.toString(), DataProfileResponseModel.class);
        } catch (Exception e) {
            log.error("Error during getting response from facebook",e);
            return new ProfileResponse("");
        }
            return new ProfileResponse(data.getGreeting()[0].getText());
    }

    private ProfileResponse(String result) {
        this.result = result;
    }
}
