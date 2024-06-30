package bookify.ms.book.infra.security.filters;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import bookify.ms.book.core.domain.User;
import bookify.ms.book.core.gateways.UsersGateway;
import bookify.ms.book.core.utils.JwtTokenService;
import bookify.ms.book.infra.security.SecurityConfiguration;
import bookify.ms.book.infra.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenService tokenService;
    private final UsersGateway usersGateway;

    public UserAuthenticationFilter(JwtTokenService tokenService, UsersGateway usersGateway) {
        this.tokenService = tokenService;
        this.usersGateway = usersGateway;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
        if(isEndpointPublic(request)) filterChain.doFilter(request, response);
        
        String token = recoveryToken(request);
        if(token == null) throw new RuntimeException("Auth token is missing");

        String subject = tokenService.getSubjectFromToken(token);
        User user = usersGateway.findByEmail(subject);
        UserDetails userDetails = new UserDetailsImpl(user);
        
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private String recoveryToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null) {
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }

    private boolean isEndpointPublic(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return Arrays.asList(SecurityConfiguration.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).contains(requestURI);
    }
    
}
