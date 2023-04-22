package br.com.cvc.evaluation.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {
    private final UserService userService;

    public AuthenticationService(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final var user = userService.findByLogin(username);

        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return user.get();
    }
}
