//package com.hi.config;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.SerializerProvider;
//
//import java.io.IOException;
//
//public class NumberToStringSerializer extends JsonSerializer<Number> {
//    @Override
//    public void serialize(Number value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//        if (value == null) gen.writeString("");
//        else gen.writeString(value.toString());
//    }
//}
