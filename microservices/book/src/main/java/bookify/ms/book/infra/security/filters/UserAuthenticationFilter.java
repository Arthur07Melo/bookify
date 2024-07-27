package bookify.ms.book.infra.security.filters;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import bookify.ms.book.core.domain.User;
import bookify.ms.book.core.exceptions.InvalidOrExpiredTokenException;
import bookify.ms.book.core.gateways.UsersGateway;
import bookify.ms.book.core.utils.JwtTokenService;
import bookify.ms.book.infra.security.SecurityConfiguration;
import bookify.ms.book.infra.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenService tokenService;
    private final UsersGateway usersGateway;
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    public UserAuthenticationFilter(JwtTokenService tokenService, UsersGateway usersGateway) {
        this.tokenService = tokenService;
        this.usersGateway = usersGateway;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
    throws ServletException, IOException {
        if(isEndpointPublic(request)) {
            filterChain.doFilter(request, response);
            return;
        };
        
        String token = recoveryToken(request);
        if(token == null) throw new RuntimeException("Auth token is missing");

        try {
            String subject = tokenService.getSubjectFromToken(token);
            User user = usersGateway.findByEmail(subject);
            UserDetails userDetails = new UserDetailsImpl(user);
            
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } catch (InvalidOrExpiredTokenException e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
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
