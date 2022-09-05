package br.com.cvc.evaluation.service;

import static br.com.cvc.evaluation.fixtures.FixtureUtil.nextUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @Mock
    UserService userService;
    @InjectMocks
    AuthenticationService authenticationService;

    @Test
    void testLoadUserByUsername() {
        // Arranges
        final var user = nextUser();
        when(userService.findByLogin(anyString())).thenReturn(Optional.of(user));

        // Act
        final var result = authenticationService.loadUserByUsername(user.getUsername());

        // Assert
        assertEquals(user, result);
    }

    @Test
    void testUserNotFound() {
        when(userService.findByLogin(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                        () -> this.authenticationService.loadUserByUsername(""));

    }
}