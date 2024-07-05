package bookify.ms.book.infra.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import bookify.ms.book.core.domain.User;
import bookify.ms.book.core.utils.AuthenticationService;

public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authManager;

    public AuthenticationServiceImpl(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public User authenticate(String email, String password) {
        var usernamePwdToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication auth = authManager.authenticate(usernamePwdToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        
        var user = userDetails.getUser();
        return user;
    }
}
