package br.com.cvc.evaluation.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Profile implements GrantedAuthority {
    private Integer id;
    private String name;

    @Override
    public String getAuthority() {
        return this.name;
    }
}
