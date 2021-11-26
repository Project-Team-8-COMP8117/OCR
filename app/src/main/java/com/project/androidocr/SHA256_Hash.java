package com.project.androidocr;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256_Hash {
    public static String Convert(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(input.getBytes());
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        // Get complete hashed password in hex format
        return sb.toString().toUpperCase();
    }
}
