package bookify.ms.book.core.gateways;

import bookify.ms.book.core.domain.User;

public interface UserGateway {
    public User save(User user);
    public User findByEmail(String email);
}
