package com.aliashik.service;

import com.aliashik.filter.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTService {

    public static String createJWT(String id, String issuer, String subject, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Key key = generateKey();

        //TODO get user roles from db by username
        Map userRoles = new HashMap();
        userRoles.put("Roles", new ArrayList<Role>(){{
            add(Role.ROLE_ADMIN);
            add(Role.ROLE_CLIENT);
        }});

        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .addClaims(userRoles)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, key);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(generateKey())
                .parseClaimsJws(token)
                .getBody();
    }

    public static Key generateKey() {
        String keyString = "aliashik";
        Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
        return key;
    }
}