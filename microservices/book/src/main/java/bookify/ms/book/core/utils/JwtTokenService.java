package bookify.ms.book.core.utils;

import bookify.ms.book.core.exceptions.InvalidOrExpiredTokenException;

public interface JwtTokenService {
    public String generateToken(String email);
    public String getSubjectFromToken(String token) throws InvalidOrExpiredTokenException;
}
