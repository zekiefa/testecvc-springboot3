package br.com.cvc.evaluation.service;

import java.util.Optional;

import br.com.cvc.evaluation.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final String login;
    private final String passwd;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(@Value("${user.login}") final String login,
                    @Value("${user.passwd}") final String passwd) {
        this.login = login;
        this.passwd = passwd;
    }

    public Optional<User> findByLogin(final String login) {
        // o ideal é ter um banco de dados para consultar o usuário
        if (login.equals(this.login))
            return Optional.of(User.builder()
                        .username(login)
                        .password(this.encoder.encode(this.passwd)).build());

        return Optional.empty();
    }
}
