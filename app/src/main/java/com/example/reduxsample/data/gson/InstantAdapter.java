package com.leeco.motor.data.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.threeten.bp.Instant;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by anlijiu on 17-5-24.
 */



public class InstantAdapter extends TypeAdapter<Instant> {

    @Override public void write(JsonWriter out, Instant value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.toEpochMilli());
        }
    }

    @Override public Instant read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        Long value = in.nextLong();

        return Instant.ofEpochMilli(value);
    }
}