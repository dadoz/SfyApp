package com.application.sfy.data.remote.services.gson;

import com.application.sfy.data.model.Lyric;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * track json to a list of track item
 */
public class LyricsJsonDeserializer implements JsonDeserializer<Lyric> {
    @Override
    public Lyric deserialize(JsonElement json, Type typeOfT,
                             JsonDeserializationContext context) throws JsonParseException {
        return new Gson().fromJson(json.getAsJsonObject().get("message").getAsJsonObject().get("body")
                .getAsJsonObject().get("lyrics").getAsJsonObject(), typeOfT);
    }
}