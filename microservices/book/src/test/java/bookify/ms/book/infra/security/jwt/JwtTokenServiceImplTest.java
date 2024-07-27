package bookify.ms.book.infra.security.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import bookify.ms.book.core.exceptions.InvalidOrExpiredTokenException;

public class JwtTokenServiceImplTest {
    final String ISSUER = "microservice-book";
    final Algorithm ALGORITHM = Algorithm.HMAC256("test_secret_key");
    
    JwtTokenServiceImpl jwtTokenService = new JwtTokenServiceImpl("test_secret_key");

    @Test
    public void testSuccessfullyGenerateToken(){
        String token = jwtTokenService.generateToken("teste@teste.com");
        var decodedToken = JWT.require(ALGORITHM)
            .withIssuer(ISSUER)
            .build()
            .verify(token);
        
        assertEquals("teste@teste.com", decodedToken.getSubject());
    } 

    @Test
    public void testSuccessfullyDecodeToken() throws InvalidOrExpiredTokenException{
        String expectedSubject = "teste2@teste.com";
        String encodedToken = JWT.create()
        .withIssuer(ISSUER)
        .withSubject(expectedSubject)
        .sign(ALGORITHM);
    
        String subject = jwtTokenService.getSubjectFromToken(encodedToken);
        assertEquals(expectedSubject, subject);
    }

}
