package com.leeco.motor.data.gson;

/**
 * Created by anlijiu on 17-5-24.
 */

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.threeten.bp.Instant;
import org.threeten.bp.format.DateTimeFormatter;

import java.lang.reflect.Type;


/**
 * GSON serialiser/deserialiser for converting {@link Instant} objects.
 */
public class InstantConverter
        implements JsonSerializer<Instant>, JsonDeserializer<Instant> {

    public InstantConverter() {
    }

    @Override
    public JsonElement serialize(final Instant src, final Type typeOfSrc,
                                 final JsonSerializationContext context) {
        return new JsonPrimitive(src.toEpochMilli());
    }

    @Override
    public Instant deserialize(final JsonElement json, final Type typeOfT,
                                 final JsonDeserializationContext context) throws JsonParseException {
        return Instant.ofEpochMilli(json.getAsLong());
    }
}
