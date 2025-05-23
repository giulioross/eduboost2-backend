package com.example.eduboost_backend.dto.auth;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;

public class JwtKeyGenerator {
    public static void main(String[] args) {
        byte[] key = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
        String base64Key = Base64.getEncoder().encodeToString(key);
        System.out.println("Chiave BASE64 sicura per HS512:");
        System.out.println(base64Key);
    }
}
