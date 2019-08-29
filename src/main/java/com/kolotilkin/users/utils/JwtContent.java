package com.kolotilkin.users.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;

public class JwtContent {

    private String data;
    private Long iat;
    private Long exp;

    public JwtContent(String userGuid, String email) {
        this.data = String.format("{\"guid\":\"%s\",\"email\":\"%s\"}",userGuid, email);

        long current_seconds = Instant.now().getEpochSecond();
        this.iat = current_seconds;
        this.exp = current_seconds + 60000L;
    }

    public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            return mapper.writeValueAsString(this);
        } catch (Exception ex) {
            throw new RuntimeException("Unable to serialize JWT Content");
        }
    }
}
