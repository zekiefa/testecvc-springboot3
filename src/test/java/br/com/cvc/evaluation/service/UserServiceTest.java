package br.com.cvc.evaluation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import br.com.cvc.evaluation.domain.User;
import org.junit.jupiter.api.Test;

class UserServiceTest {
    private static final String LOGIN = "usuario";
    private static final String PASSWD = "senha";
    UserService userService = new UserService(LOGIN, PASSWD);

    @Test
    void testFindByLogin() {
        final var result = userService.findByLogin(LOGIN)
                        .orElse(User.builder().build());

        assertEquals(LOGIN, result.getUsername());
    }

    @Test
    void testFindByLoginNotFound() {
        final var result = userService.findByLogin(UUID.randomUUID().toString());

        assertTrue(result.isEmpty());
    }
}