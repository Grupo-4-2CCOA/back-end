package sptech.school.projetoPI.old.core.application.dto.login;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sptech.school.projetoPI.old.core.domains.User;

import java.util.Collection;

@Getter
@Setter
public class UserDetailsDto implements UserDetails {
    private final String nome;
    private final String email;
    private final String senha;

    public UserDetailsDto(User usuario) {
        this.nome = usuario.getName();
        this.email = usuario.getEmail();
        this.senha = usuario.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
