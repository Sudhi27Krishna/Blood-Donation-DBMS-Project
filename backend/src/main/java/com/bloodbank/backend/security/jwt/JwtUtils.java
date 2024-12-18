package com.bloodbank.backend.security.jwt;

import com.bloodbank.backend.security.user.BBUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {
    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;

    @Value("${auth.token.expirationInMils}")
    private int expirationTime;

    public String generateJwtToken(Authentication authentication) {
        BBUserDetails userDetails = (BBUserDetails) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("id", userDetails.getId())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expirationTime))
                .signWith(key(), SignatureAlgorithm.HS256).compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // Method to extract username from the JWT token
    public String getUsernameFromJwtToken(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    // Method to extract a specific claim from the JWT token
    private <T> T extractClaim(String jwtToken, java.util.function.Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    // Method to extract all claims from the JWT token
    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(key())
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    // Method to validate a JWT token
    public boolean validateJwtToken(String jwtToken) {
        try {
            return !isTokenExpired(jwtToken);
        } catch (SignatureException | IllegalArgumentException e) {
            return false; // Invalid token
        }
    }

    // Method to check if the token is expired
    private boolean isTokenExpired(String jwtToken) {
        final Claims claims = extractAllClaims(jwtToken);
        return claims.getExpiration().before(new java.util.Date());
    }
}
