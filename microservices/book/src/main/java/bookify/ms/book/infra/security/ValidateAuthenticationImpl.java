package bookify.ms.book.infra.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import bookify.ms.book.core.domain.User;
import bookify.ms.book.core.utils.ValidateAuthentication;

public class ValidateAuthenticationImpl implements ValidateAuthentication {

    @Override
    public User authenticate(String email, String password) {
        Authentication auth = new UsernamePasswordAuthenticationToken(email, password);
        User user = ((UserDetailsImpl) auth.getPrincipal()).getUser();
        return user;
    }
    
}
