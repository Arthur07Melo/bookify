package bookify.ms.book.core.utils;

import bookify.ms.book.core.domain.User;

public interface ValidateAuthentication {
    public User authenticate(String email, String password);
}
