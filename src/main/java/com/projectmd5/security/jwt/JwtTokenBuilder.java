package com.projectmd5.security.jwt;

import com.projectmd5.exception.BadRequestException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
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
         Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
         return true;
      } catch (SignatureException e) {
         throw new BadRequestException("Invalid JWT signature" + e.getMessage());
      } catch (MalformedJwtException e) {
         throw new BadRequestException("Invalid JWT token" + e.getMessage());
      } catch (ExpiredJwtException e) {
         throw new BadRequestException("JWT token is expired" + e.getMessage());
      } catch (UnsupportedJwtException e) {
         throw new BadRequestException("JWT token is unsupported" + e.getMessage());
      } catch (IllegalArgumentException e) {
         throw new BadRequestException("JWT claims string is empty" + e.getMessage());
      }

   }

   private Key key() {
      return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
   }
}
