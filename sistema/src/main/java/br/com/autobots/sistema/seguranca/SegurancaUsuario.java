package br.com.autobots.sistema.seguranca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.autobots.sistema.entidades.Usuario;

public class SegurancaUsuario implements UserDetails {
  private final Usuario usuario;

  public SegurancaUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    switch (usuario.getPerfil()) {
      case ADMIN:
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        break;
      case GERENTE:
        authorities.add(new SimpleGrantedAuthority("ROLE_GERENTE"));
        break;
      case VENDEDOR:
        authorities.add(new SimpleGrantedAuthority("ROLE_VENDEDOR"));
        break;
      case CLIENTE:
        authorities.add(new SimpleGrantedAuthority("ROLE_CLIENTE"));
        break;
      default:
        throw new IllegalArgumentException("Perfil de usuário inválido: " +
            usuario.getPerfil());
    }
    System.out.println("Authorities: " + authorities);
    return authorities;
  }

  @Override
  public String getPassword() {
    return "";
  }

  @Override
  public String getUsername() {
    return usuario.getEmail();
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
    return !usuario.getInativo();
  }

  public Usuario getUsuario() {
    return usuario;
  }
}
