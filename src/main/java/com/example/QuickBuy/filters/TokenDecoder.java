package com.example.QuickBuy.filters;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TokenDecoder {

    public String getToken(String authHeader) {
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }else {
            return "Auth headers required";
        }
    }
}
