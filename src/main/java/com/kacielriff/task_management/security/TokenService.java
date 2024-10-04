package com.kacielriff.task_management.security;

import com.kacielriff.task_management.entity.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {
    private static final String TOKEN_PREFIX = "Bearer";

    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + Long.parseLong(expiration));

        return TOKEN_PREFIX + " " +
                Jwts.builder()
                        .setIssuer("task-management")
                        .setSubject(user.getId().toString())
                        .setIssuedAt(now)
                        .setExpiration(exp)
                        .signWith(SignatureAlgorithm.HS256, secret)
                        .compact();
    }

    public UsernamePasswordAuthenticationToken isValid(String token) throws Exception {
        if (token == null) return null;

        String userId = decryptToken(token).getSubject();

        return userId == null
                ? null
                : new UsernamePasswordAuthenticationToken(userId, null, null);

    }

    public String generateRecoverPasswordToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("task-management")
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(expiration)))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Claims decryptToken(String token) throws Exception {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new Exception("Token expirado");
        } catch (UnsupportedJwtException e) {
            throw new Exception("Token não suportado");
        } catch (MalformedJwtException e) {
            throw new Exception("Token malformado");
        } catch (SignatureException e) {
            throw new Exception("Assinatura inválida");
        } catch (IllegalArgumentException e) {
            throw new Exception("Token inválido ou está vazio");
        }
    }
}
