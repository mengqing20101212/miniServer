package ly.gmserver.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private static final String SECRET = "LyMiniGameGMServerSecretKey2024VeryLongAndSecure!@#$%";
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000L; // 24 hours

    private final SecretKey secretKey;

    public JwtUtil() {
        this.secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Long adminId, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("adminId", adminId);
        claims.put("username", username);
        return Jwts.builder()
            .claims(claims)
            .subject(String.valueOf(adminId))
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
            .signWith(secretKey)
            .compact();
    }

    public Claims parseToken(String token) {
        try {
            return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    public boolean validateToken(String token) {
        return parseToken(token) != null;
    }

    public Long getAdminId(String token) {
        Claims claims = parseToken(token);
        if (claims != null) {
            return claims.get("adminId", Long.class);
        }
        return null;
    }

    public String getUsername(String token) {
        Claims claims = parseToken(token);
        if (claims != null) {
            return claims.get("username", String.class);
        }
        return null;
    }
}
