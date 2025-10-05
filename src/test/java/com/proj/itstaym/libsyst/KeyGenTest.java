package com.proj.itstaym.libsyst;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Base64;

public class KeyGenTest {

    public static void main(String[] args) {
        SecretKey key = Jwts.SIG.HS256.key().build(); // secure 256-bit key
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println(base64Key);
    }
}
