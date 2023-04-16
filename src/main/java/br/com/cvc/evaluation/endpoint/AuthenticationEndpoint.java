package br.com.cvc.evaluation.endpoint;

import br.com.cvc.evaluation.domain.Login;
import br.com.cvc.evaluation.domain.Token;
import br.com.cvc.evaluation.service.AuthenticationService;
import br.com.cvc.evaluation.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationEndpoint {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<Token> login(@Validated @RequestBody final Login loginAuth) {
        try {
            final var found = this.authenticationService
                            .loadUserByUsername(loginAuth.user());
            final var token = tokenService.generateToken(found.getUsername());
            return ResponseEntity
                            .ok(new Token("Bearer",token));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }
}
