package br.com.cvc.evaluation.config;

import java.io.IOException;
import java.util.Optional;

import br.com.cvc.evaluation.service.TokenService;
import br.com.cvc.evaluation.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UserService userService;

    public TokenAuthenticationFilter(final TokenService tokenService,
                    final UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                    final HttpServletResponse response,
                    final FilterChain filterChain) throws ServletException, IOException {
        final var tokenFromHeader = getTokenFromHeader(request);
        final var tokenValid = tokenService.isTokenValid(tokenFromHeader);

        if (tokenValid) {
            this.authenticate(tokenFromHeader);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeader(final HttpServletRequest request) {
        final var token = request.getHeader("Authorization");

        if(token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7);
    }

    private void authenticate(final String tokenFromHeader) {
        final var username = this.tokenService.extractUsername(tokenFromHeader);

        if (Optional.ofNullable(username).isPresent() && SecurityContextHolder.getContext().getAuthentication() == null) {
            final var user = this.userService.findByLogin(username)
                            .orElseThrow(() -> new UsernameNotFoundException("User not present"));

            if (this.tokenService.validateToken(tokenFromHeader, user)) {
                final var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user,
                                null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
    }
}
