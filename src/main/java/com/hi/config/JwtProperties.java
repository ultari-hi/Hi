package com.hi.config;

public interface JwtProperties {
    String SECRET = "hi";
    int EXPIRATION_TIME = 1000*60*300;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
