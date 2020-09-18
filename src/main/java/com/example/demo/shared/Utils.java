package com.example.demo.shared;

import com.example.demo.config.Constants;
import com.example.demo.security.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Controller;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

import static com.example.demo.security.SecurityConstants.*;

@Controller
public class Utils {
    private final Random RANDOM = new SecureRandom();

    public String generateUserId(int length) {
        return generateRandomString(length);
    }

    private String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(Constants.RANDOM.ALPHABET.charAt(RANDOM.nextInt(Constants.RANDOM.ALPHABET.length())));
        }

        return new String(returnValue);
    }



}