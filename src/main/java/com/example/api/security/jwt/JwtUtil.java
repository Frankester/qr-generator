package com.example.api.security.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

        private final SecretKey phraseSecret= Keys.secretKeyFor(SignatureAlgorithm.HS256);

        @Value("${app.jwt.expiration}")
        private int jwtExpiration;

        private final Logger log = LoggerFactory.getLogger(JwtUtil.class);


    public String createTokenJwt(Authentication authentication){
        UserDetails user = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration))

                .signWith(phraseSecret)
                .compact();
    }

    public String getUsernameFromJwt(String jwt){
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(phraseSecret)
                .build()
                .parseClaimsJws(jwt);

        return jws.getBody().getSubject();
    }

    public boolean isJwtValid(String jwt){

        try{
            Jwts.parserBuilder()
                    .setSigningKey(phraseSecret)
                    .build()
                    .parseClaimsJws(jwt);

            return true;
        } catch(JwtException  e){
            log.error("JWT Exception: {}", e.getMessage());
        }

        return false;
    }

}
