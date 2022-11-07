//package com.hi.config;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.SerializerProvider;
//
//import java.io.IOException;
//
////응답시 null 값을 ""으로 치환
////CustomObjectMapper 에 등록해줘야함
//public class NullSerializer extends JsonSerializer<Object> {
//    public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider)
//            throws IOException, JsonProcessingException {
//        jgen.writeString("");
//    }
//}
