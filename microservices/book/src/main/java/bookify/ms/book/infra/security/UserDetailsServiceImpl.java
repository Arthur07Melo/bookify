package bookify.ms.book.infra.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import bookify.ms.book.core.gateways.UsersGateway;

public class UserDetailsServiceImpl implements UserDetailsService {
    private UsersGateway usersGateway;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = usersGateway.findByEmail(username);
        if (user == null) throw new RuntimeException("Username not found");
        return new UserDetailsImpl(user);
    }
    
}
