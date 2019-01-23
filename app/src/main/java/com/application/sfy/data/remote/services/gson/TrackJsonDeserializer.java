package com.application.sfy.data.remote.services.gson;

import com.application.sfy.data.model.Track;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.List;

/**
 * track json to a list of track item
 */
public class TrackJsonDeserializer implements JsonDeserializer<List<Track>> {
    @Override
    public List<Track> deserialize(JsonElement json, Type typeOfT,
                                   JsonDeserializationContext context) throws JsonParseException {
        //TODO define it
        return null;
    }
}