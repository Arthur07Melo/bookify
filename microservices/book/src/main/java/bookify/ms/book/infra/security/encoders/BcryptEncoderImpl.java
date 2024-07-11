package bookify.ms.book.infra.security.encoders;

import org.springframework.security.crypto.password.PasswordEncoder;

import bookify.ms.book.core.utils.Encoder;

public class BcryptEncoderImpl implements Encoder {
    private final PasswordEncoder passwordEncoder;

    public BcryptEncoderImpl(PasswordEncoder encoder) {
        this.passwordEncoder = encoder;
    }

    @Override
    public String encodePassword(String pwd) {
        return passwordEncoder.encode(pwd);
    }
}
