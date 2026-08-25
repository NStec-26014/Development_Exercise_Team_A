package com.example.fullness.stationary.config;

import java.math.BigInteger;
import java.security.MessageDigest;

public class TextEncoder {

    public String toHash(String password) {

        String toReturn = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(password.getBytes("utf8"));
            toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toReturn;

    }
}
