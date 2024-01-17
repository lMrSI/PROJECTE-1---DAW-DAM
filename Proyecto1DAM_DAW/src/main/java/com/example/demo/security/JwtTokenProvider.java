package com.example.demo.security;

import com.example.demo.model.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;


@Component
@SuppressWarnings("removal")
public class JwtTokenProvider {
     Logger log = LoggerFactory.getLogger(this.getClass());
     @Value("${app.security.jwt.secret}")
     private String JwtSecret;

     @Value("${app.security.jwt.expiration}")
    private Long jwtDurationSeconds;

     public String generateToken(Authentication authentication) {
         Usuario usuario = (Usuario) authentication.getPrincipal();

         return Jwts.builder()
                 .signWith(Keys.hmacShaKeyFor(JwtSecret.getBytes()), SignatureAlgorithm.HS512)
                 .setHeaderParam("typ", "JWT")
                 .setSubject(Long.toString(usuario.getIdUsuario()))
                 .setIssuedAt(new Date())
                 .setExpiration(new Date(System.currentTimeMillis() + (jwtDurationSeconds * 1000)))
                 .claim("username", usuario.getUsername())
                 .claim("email", usuario.getEmail())
                 .compact();
     }

     public boolean isValidToken(String token) {
        if(!StringUtils.hasLength(token))
            return false;

        try {
            JwtParser validator = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(JwtSecret.getBytes())).build();
            validator.parseClaimsJws(token);
            return true;

        } catch (SignatureException e) {
            log.info("Error en la firma del token", e);
        } catch (MalformedJwtException | UnsupportedJwtException e) {
            log.info("Token incorrecto", e);
        } catch (ExpiredJwtException e) {
            log.info("Token expirado", e);
        }
        return false;
     }

    public String getUsernameFromToken(String token) {
        JwtParser parser = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(JwtSecret.getBytes()))
                .build();

        Claims claims = parser.parseClaimsJws(token).getBody();
        return claims.get("username").toString();
    }

}
