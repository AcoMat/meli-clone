package unq.pdes._5.g1.segui_tus_compras.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration:21600000}") // Default: 6 hours in milliseconds
    private long expirationTime;

    public String generateToken(Long id) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withSubject(id.toString())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(algorithm);
    }

    public String generateAdminToken(Long id) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withSubject(id.toString())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .withArrayClaim("roles", new String[]{"ADMIN"})
                .sign(algorithm);
    }

    public Long validateTokenAndGetUserId(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token.replace("Bearer ", ""));
        return jwt.getSubject() != null ? Long.valueOf(jwt.getSubject()) : null;
    }

    public Long validateAdminTokenAndGetUserId(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token.replace("Bearer ", ""));
        String[] roles = jwt.getClaim("roles").asArray(String.class);
        if (roles != null) {
            for (String role : roles) {
                if ("ADMIN".equals(role)) {
                    return jwt.getSubject() != null ? Long.valueOf(jwt.getSubject()) : null;
                }
            }
        }
        return null;
    }
}