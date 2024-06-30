package bookify.ms.book.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import bookify.ms.book.core.utils.Encoder;
import bookify.ms.book.core.utils.ValidateAuthentication;
import bookify.ms.book.infra.security.encoders.BcryptEncoderImpl;
import bookify.ms.book.infra.security.filters.UserAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final UserAuthenticationFilter userAuthenticationFilter;

    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
        "/book/users/login",
        "/book/users/register"
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
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(req -> {
            req
            .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
            .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMIN")
            .requestMatchers(ENDPOINTS_USER).hasRole("USAER")
            .anyRequest().permitAll();
        });
        httpSecurity.addFilterBefore(userAuthenticationFilter, UserAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager AuthenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //==============================

    @Bean
    ValidateAuthentication validateAuthentication(){
        return new ValidateAuthenticationImpl();
    }

    @Bean
    Encoder encoder(PasswordEncoder pwdEncoder){
        return new BcryptEncoderImpl(pwdEncoder);
    }
}
