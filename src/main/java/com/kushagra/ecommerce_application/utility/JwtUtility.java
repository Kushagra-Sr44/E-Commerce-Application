package com.kushagra.ecommerce_application.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Component
public class JwtUtility   {
    private String secrect_Key="jdsk$37mnnnnnnnmmmmmmmmmmmmmnn8WR8new#32sdSS";
    private SecretKey getSigningKey(){
        return  Keys.hmacShaKeyFor(secrect_Key.getBytes());
    }
    public String createToken(String username){
        Map<String,Object> claims=new HashMap<>();
        return generateToken(username,claims);
    }
    public boolean isValid(String token){
        Date exp=extractExpiration(token);
        return exp.after(new Date(System.currentTimeMillis()));
    }
   public String extractUsername(String token){
       Claims claims=extractAllClaims(token);
       return claims.getSubject();
   }
   private Date extractExpiration(String token){
        Claims claims=extractAllClaims(token);
        return claims.getExpiration();
   }
   private Claims extractAllClaims(String token){
        return Jwts.parser().
                verifyWith(getSigningKey())
                .build().parseSignedClaims(token)
                .getPayload();

   }
    private String generateToken(String subject,Map<String ,Object>claims){
        return Jwts.builder().
                claims(claims)
                .subject(subject)
                .header().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+60*60   *1000))
                .signWith(getSigningKey()).compact();

    }
}
