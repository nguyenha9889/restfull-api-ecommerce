package com.projectmd5.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Lớp này có 3 chức năng chính:
 * generate JWT, refresh Token
 * get JWT, refresh Token from Cookies
 * validate a JWT: JWT Access Token is expired with ExpiredJwtException
 */
@Component
@Slf4j
public class JwtUtils {
   @Value("${jwt.secret-key}")
   private String secretKey;
   @Value("${jwt.expired.access-token}")
   private long accessTokenExpiration;
   @Value("${jwt.expired.refresh-token}")
   private long refreshTokenExpiration;

   public String generateAccessToken(UserDetails userDetails) {
      return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + accessTokenExpiration))
            .signWith(key(), SignatureAlgorithm.HS256)
            .compact();
   }

   public String generateRefreshToken(UserDetails userDetails) {
      return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + refreshTokenExpiration))
            .signWith(key(), SignatureAlgorithm.HS256)
            .compact();
   }

   public String getUserNameFromToken(String token) {
      return Jwts.parserBuilder().setSigningKey(key()).build()
            .parseClaimsJws(token).getBody().getSubject();
   }

   public boolean validateToken(String token) {
      try {
         Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
         return true;
      } catch (SignatureException e) {
         log.error("Invalid JWT signature: {}", e.getMessage());
      } catch (MalformedJwtException e) {
         log.error("Invalid JWT token: {}", e.getMessage());
      } catch (ExpiredJwtException e) {
         log.error("JWT token is expired: {}", e.getMessage());
      } catch (UnsupportedJwtException e) {
         log.error("JWT token is unsupported: {}", e.getMessage());
      } catch (IllegalArgumentException e) {
         log.error("JWT claims string is empty: {}", e.getMessage());
      }

      return false;
   }

   private Key key() {
      byte[] keyBytes = Decoders.BASE64.decode(secretKey);
      return Keys.hmacShaKeyFor(keyBytes);
   }
}
