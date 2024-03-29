package com.projectmd5.security.jwt;

import com.projectmd5.exception.JWTException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenBuilder {
   @Value("${jwt.secret-key}")
   private String secretKey;
   @Value("${jwt.expired.access-token}")
   private long accessTokenExpiration;
   @Value("${jwt.expired.refresh-token}")
   private long refreshTokenExpiration;

   public String generateAccessToken(UserDetails principal) {
      return Jwts.builder()
            .setSubject(principal.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + accessTokenExpiration))
            .signWith(key(), SignatureAlgorithm.HS256)
            .compact();
   }

   public String generateRefreshToken(UserDetails principal) {
      return Jwts.builder()
            .setSubject(principal.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
            .signWith(key(), SignatureAlgorithm.HS256)
            .compact();
   }

   public String getUserNameFromToken(String token) {
      return Jwts.parserBuilder().setSigningKey(key()).build()
            .parseClaimsJws(token).getBody().getSubject();
   }

   public boolean validateToken(String token) {
      try {
         Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
         return true;
      } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
               IllegalArgumentException e) {
         throw new JWTException(e.getMessage());
      }

   }

   public Long getExpiredFromToken(String token) {
      return Jwts.parserBuilder().setSigningKey(key()).build()
            .parseClaimsJws(token).getBody().getExpiration().getTime();
   }

   public String generateNewAccessToken(String username) {
      return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + accessTokenExpiration))
            .signWith(key(), SignatureAlgorithm.HS256)
            .compact();
   }
   private Key key() {
      return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
   }
}
