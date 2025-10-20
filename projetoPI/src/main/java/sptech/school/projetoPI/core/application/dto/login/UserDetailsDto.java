package sptech.school.projetoPI.core.application.dto.login;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import sptech.school.projetoPI.core.domains.RoleDomain;
import sptech.school.projetoPI.core.domains.UserDomain;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class UserDetailsDto implements UserDetails {

    private final Integer id;
    private final String nome;
    private final String email;
    private final String senha;
    private final RoleDomain roleDomain;

    public UserDetailsDto(UserDomain usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getName();
        this.email = usuario.getEmail();
        this.senha = "";
        this.roleDomain = usuario.getRoleDomain();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roleDomain == null || roleDomain.getName() == null) {
            // caso o usuário não tenha role atribuída
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return List.of(new SimpleGrantedAuthority("ROLE_" + roleDomain.getName().toUpperCase()));
    }
    @Override
    public String getUsername() {
        return email;
    }

    public String getRoleName() {
        return roleDomain != null ? roleDomain.getName() : null;
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

    @Override
    public String getPassword() {
        return "";
    }
}
