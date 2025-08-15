package com.example.api.models.qrgenerators;

import java.security.SecureRandom;

public class RandomHashGenerator {

    private static final String ALPHANUMERIC_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final int LENGTH = 7;

    private RandomHashGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static String generateRandomHashName(){
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder(LENGTH);

        for(int i = 0; i< LENGTH; i++){
            int randomIndex =random.nextInt(ALPHANUMERIC_CHARS.length());
            stringBuilder.append(ALPHANUMERIC_CHARS.charAt(randomIndex));
        }

        return stringBuilder.toString();
    }
}
