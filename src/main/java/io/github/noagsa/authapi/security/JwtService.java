package io.github.noagsa.authapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    /**
     * Generates a JWT token containing the email as the subject, with an expiration time of 24 hours.
     * @param email the email to include in the token
     * @return a JWT token as a String
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }

    /**
     * Extracts the email from the given JWT token.
     * @param token the JWT token to extract the email from
     * @return the email contained in the token
     */
    public String extractEmail(String token){
        return extractClaims(token)
                .getSubject();
    }

    /**
     * Validates the given JWT token.
     * @param token the JWT token to validate
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token){
        try {
            extractEmail(token);
            return extractExpiration(token).after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    private Date extractExpiration(String token) {
        return extractClaims(token)
                .getExpiration();
    }
}
