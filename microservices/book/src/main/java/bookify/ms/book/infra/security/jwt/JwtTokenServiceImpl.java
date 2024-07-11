package bookify.ms.book.infra.security.jwt;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import bookify.ms.book.core.utils.JwtTokenService;

public class JwtTokenServiceImpl implements JwtTokenService{
    private final String SECRET_KEY;
    private final String ISSUER = "microservice-book";

    public JwtTokenServiceImpl(String secretKey) {
        SECRET_KEY = secretKey;
    }

    @Override
    public String generateToken(String email){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(creationDate())
                    .withExpiresAt(expirationDate())
                    .withSubject(email)
                    .sign(algorithm);

        } catch (Exception e) {
            throw new JWTVerificationException("Token inválido ou expirado");
        }
    }

    @Override
    public String getSubjectFromToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        try {
            return JWT.require(algorithm)
            .withIssuer(ISSUER)
            .build()
            .verify(token)
            .getSubject();   
            
        } catch (Exception e) {
            throw new JWTVerificationException("Token inválido ou expirado");
        }
    }

    private Instant creationDate(){
        return ZonedDateTime.now(ZoneId.of("UTC")).toInstant();
    }
    private Instant expirationDate(){
        return ZonedDateTime.now(ZoneId.of("UTC")).plusHours(4).toInstant();
    }
}
