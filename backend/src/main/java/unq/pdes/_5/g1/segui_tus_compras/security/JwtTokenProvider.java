package unq.pdes._5.g1.segui_tus_compras.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String SECRET = "mi_clave_secreta_segura";
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 6;
    private final Algorithm algorithm = Algorithm.HMAC256(SECRET);

    public String generateToken(Long id) {
        return JWT.create()
                .withSubject(id.toString())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    public Long validateTokenAndGetUserId(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token.replace("Bearer ", ""));
        return jwt.getSubject() != null ? Long.valueOf(jwt.getSubject()) : null;
    }
}