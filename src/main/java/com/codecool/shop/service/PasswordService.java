package com.codecool.shop.service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import org.springframework.security.crypto.bcrypt.BCrypt;


public class PasswordService {


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
}
