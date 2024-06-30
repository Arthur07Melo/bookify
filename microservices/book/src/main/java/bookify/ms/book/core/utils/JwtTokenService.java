package bookify.ms.book.core.utils;

public interface JwtTokenService {
    public String generateToken(String email);
    public String getSubjectFromToken(String token);
}
