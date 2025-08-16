package com.vkt.pms.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
import java.util.Base64;

public class CustomKeyGenerator {
    private static void base64EncodedStringGenerator() {
        byte[] key = new byte[64]; // 512-bit key
        new SecureRandom().nextBytes(key);
        System.out.println(Base64.getEncoder().encodeToString(key));
    }


    private static void BCryptHashStringGenerator(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashed = encoder.encode("Plain text");
        System.out.println("BCrypt hash: " +hashed);
    }

    public static void main(String[] args) {
        base64EncodedStringGenerator();    // Generate secret key

//        BCryptHashStringGenerator();       // Generate BCrypt Hashing string
    }
}
