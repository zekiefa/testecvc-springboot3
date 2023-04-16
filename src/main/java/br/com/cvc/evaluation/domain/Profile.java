package br.com.cvc.evaluation.domain;

import org.springframework.security.core.GrantedAuthority;

public record Profile (Integer id, String name) implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return this.name;
    }
}
