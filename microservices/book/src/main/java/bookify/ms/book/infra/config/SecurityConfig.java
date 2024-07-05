package bookify.ms.book.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import bookify.ms.book.core.gateways.UsersGateway;
import bookify.ms.book.core.usecases.LoginUserUseCase;
import bookify.ms.book.core.usecases.RegisterUserUseCase;
import bookify.ms.book.core.utils.Encoder;
import bookify.ms.book.core.utils.JwtTokenService;
import bookify.ms.book.core.utils.AuthenticationService;
import bookify.ms.book.data.gatewaysImpl.UsersGatewayImpl;
import bookify.ms.book.data.mappers.RoleMapper;
import bookify.ms.book.data.mappers.UserMapper;
import bookify.ms.book.data.repositories.UsersRepository;
import bookify.ms.book.infra.security.jwt.JwtTokenServiceImpl;

@Configuration
public class SecurityConfig {
    @Bean
    JwtTokenService jwtTokenService(){
        return new JwtTokenServiceImpl();
    }

    @Bean
    UserMapper userMapper(){
        return new UserMapper(new RoleMapper());
    }

    @Bean
    UsersGateway usersGateway(UsersRepository usersRepository, UserMapper userMapper) {
        return new UsersGatewayImpl(usersRepository, userMapper);
    }

    @Bean
    LoginUserUseCase loginUserUseCase(AuthenticationService validateAuthentication, JwtTokenService jwtTokenService){
        return new LoginUserUseCase(validateAuthentication, jwtTokenService);
    }

    @Bean
    RegisterUserUseCase registerUserUseCase(UsersGateway usersGateway, Encoder encoder){
        return new RegisterUserUseCase(usersGateway, encoder);
    }
}
