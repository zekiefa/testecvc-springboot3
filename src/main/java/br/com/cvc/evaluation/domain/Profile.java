package br.com.cvc.evaluation.domain;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;

@Builder
public record Profile (Integer id, String name) implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return this.name;
    }
}
