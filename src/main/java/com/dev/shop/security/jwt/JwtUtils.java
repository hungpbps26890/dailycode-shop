package com.dev.shop.security.jwt;

import com.dev.shop.constants.ErrorMessage;
import com.dev.shop.exceptions.AuthException;
import com.dev.shop.security.user.ShopUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${auth.jwt.secret-key}")
    private String secretKey;

    @Value("${auth.jwt.expiration-time}")
    private int expirationTime;

    public String generateToken(Authentication authentication) {
        ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();

        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return Jwts.builder()
                .setSubject(userDetails.getEmail())
                .claim("id", userDetails.getId())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(Instant
                        .now()
                        .plus(expirationTime, ChronoUnit.SECONDS)
                        .toEpochMilli()
                ))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException |
                 IllegalArgumentException e) {
            throw new AuthException(ErrorMessage.UNAUTHENTICATED);
        }
    }
}
