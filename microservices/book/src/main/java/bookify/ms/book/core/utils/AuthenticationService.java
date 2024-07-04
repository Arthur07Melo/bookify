package bookify.ms.book.core.utils;

import bookify.ms.book.core.domain.User;

public interface AuthenticationService {
    public User authenticate(String email, String password);
}
