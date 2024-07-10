package bookify.ms.book.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import bookify.ms.book.core.utils.Encoder;
import bookify.ms.book.core.gateways.UsersGateway;
import bookify.ms.book.core.utils.AuthenticationService;
import bookify.ms.book.infra.security.encoders.BcryptEncoderImpl;
import bookify.ms.book.infra.security.filters.UserAuthenticationFilter;
    
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final UserAuthenticationFilter userAuthenticationFilter;

    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
        "/users/login",
        "/users/register"
    };

    public static final String[] ENDPOINTS_USER = {
        "/"
    };

    public static final String[] ENDPOINTS_ADMIN = {
        "/"
    };

    public SecurityConfiguration(UserAuthenticationFilter userAuthenticationFilter) {
        this.userAuthenticationFilter = userAuthenticationFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
        .csrf(csrf -> csrf.disable())
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(req -> {
            req
            .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
            .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMIN")
            .requestMatchers(ENDPOINTS_USER).hasRole("USER")
            .anyRequest().permitAll();
        })
        .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
    }


    @Bean
    AuthenticationProvider authenticationProvider(UsersGateway usersGateway) {
        var provider =  new DaoAuthenticationProvider();
        provider.setUserDetailsService(new UserDetailsServiceImpl(usersGateway));
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //==============================

    @Bean
    AuthenticationService validateAuthentication(AuthenticationManager authManager){
        return new AuthenticationServiceImpl(authManager);
    }

    @Bean
    Encoder encoder(PasswordEncoder pwdEncoder){
        return new BcryptEncoderImpl(pwdEncoder);
    }
}
