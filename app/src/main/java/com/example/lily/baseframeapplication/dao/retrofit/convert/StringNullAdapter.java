package com.example.lily.baseframeapplication.dao.retrofit.convert;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

/**
 * Created by lily on 2017/11/9.
 */

public class StringNullAdapter extends TypeAdapter<String> {

    @Override public void write(JsonWriter out, String value)
            throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.value(value);
    }


    @Override public String read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return "";
        }
        return reader.nextString();
    }
}
