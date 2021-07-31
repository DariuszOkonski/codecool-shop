package com.codecool.shop.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.util.Base64;

import org.springframework.security.crypto.bcrypt.BCrypt;


public class PasswordService {

    SecureRandom secureRandom = new SecureRandom();
    long defaultExpiration = 259200000L; // Three days in seconds.


    public String getPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt());
//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[16];
//
//        random.nextBytes(salt);
//
//        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
//        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//        String hashToString = DatatypeConverter
//                .printHexBinary(factory.generateSecret(spec).getEncoded());
        return pw_hash;
    }

    public boolean passwordMatches(String password, String hashedPassword){
        return BCrypt.checkpw(password, hashedPassword);
    }

    public String generateToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes);
    }

    public Timestamp getExpirationDate(){
        long timeNow = new java.util.Date().getTime();

        return new Timestamp(timeNow + defaultExpiration);
    }
}
