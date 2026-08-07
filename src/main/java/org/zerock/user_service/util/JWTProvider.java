package org.zerock.user_service.util;

import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zerock.user_service.dto.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;


@Component
public class JWTProvider {


@Value("${jwt.secret}")
private String secret;


private SecretKey secretKey;


 @PostConstruct
 private void init() {
   secretKey = Keys.hmacShaKeyFor(secret.getBytes());
 }

public String generateToken(UserDTO dto, int minutes) {
   Date now = new Date();
   Date expiry = new Date(now.getTime() + (long) minutes * 60 * 1000);


   return Jwts.builder()
     .subject(dto.getUserId()) //subject로 userId지정
     .claim("username", dto.getUsername())
     .claim("rolename", dto.getRolename())
     .issuedAt(now)
     .expiration(expiry)
     .signWith(secretKey)
     .compact();
 }

 public Claims getClaims(String token) {
   try {
     return Jwts.parser()
       .verifyWith(secretKey)
       .build()
       .parseSignedClaims(token)
       .getPayload();
   } catch (ExpiredJwtException e) {
     return e.getClaims();
   }
 }


 public boolean isExpired(String token) {
   return getClaims(token).getExpiration().before(new Date());
 }

}

