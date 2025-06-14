package br.com.autobots.automanager.provedores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.autobots.automanager.entidades.Usuario;
import br.com.autobots.automanager.enums.PerfilUsuario;
import br.com.autobots.automanager.excecoes.AutenticacaoExcecao;
import br.com.autobots.automanager.seguranca.SegurancaUsuario;

@Component
public class AutenticacaoProvedor {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public boolean validarCredenciais(String email, String password) {
    var authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
    try {
      var authentication = authenticationManager.authenticate(authenticationToken);
      return authentication.isAuthenticated();
    } catch (DisabledException e) {
      throw new AutenticacaoExcecao("Usuário desabilitado");
    } catch (BadCredentialsException e) {
      return false;
    } catch (Exception e) {
      return false;
    }
  }

  public Usuario registrar(Usuario usuario) {
    var credencial = usuario.getCredencial();
    var encryptedPassword = passwordEncoder.encode(credencial.getSenha());
    credencial.setSenha(encryptedPassword);
    usuario.setCredencial(credencial);
    return usuario;
  }

  public Usuario getUsuario() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof SegurancaUsuario segurancaUsuario) {
      return segurancaUsuario.getUsuario();
    }
    throw new AutenticacaoExcecao("Usuário não autenticado");
  }

  public PerfilUsuario getPerfil() {
    var usuario = getUsuario();
    return usuario.getPerfil();
  }

  public Long getUsuarioId() {
    var usuario = getUsuario();
    return usuario.getId();
  }

}
