package br.com.autobots.autenticacao.provedores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.autobots.autenticacao.entidades.Credencial;
import br.com.autobots.autenticacao.excecoes.AutenticacaoExcecao;
import br.com.autobots.autenticacao.repositorios.CredencialRepositorio;

@Component
public class AutenticacaoProvedor {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private CredencialRepositorio credencialRepositorio;

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

  public void registrar(Credencial credencial) {
    var encryptedPassword = passwordEncoder.encode(credencial.getSenha());
    credencial.setSenha(encryptedPassword);
    credencialRepositorio.save(credencial);
  }
}
