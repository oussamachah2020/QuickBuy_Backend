package com.example.QuickBuy.services.owner;

import com.example.QuickBuy.models.owner.Owner;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private String SECRET_KEY = "6c651725d9989d0120a0d04229dc6444de7f42c88e80b827f7e058518ed716cd";

    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isValid(String token, Owner user) {
        String id = extractUserId(token);
        return (id.equals(user.getId()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    public String generateAccessToken(Owner user) {

        return Jwts.builder()
                .subject(user.getId()) // Or use any other unique field
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // 24 hours
                .signWith(getSigningKey()) // Sign the token
                .compact();
    }

    public String generateRefreshToken(Owner user) {
        return Jwts.builder().subject(user.getId()).issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() + 24*60*60*1000)).signWith(getSigningKey()).compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
