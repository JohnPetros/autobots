package br.com.autobots.sistema.servicos;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.autobots.sistema.excecoes.AutenticacaoExcecao;
import br.com.autobots.sistema.repositorios.UsuarioRepositorio;
import br.com.autobots.sistema.seguranca.SegurancaUsuario;

@Service
public class CarregarSegurancaUsuarioServico implements UserDetailsService {
  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  @Override
  public UserDetails loadUserByUsername(String usuarioEmail) throws UsernameNotFoundException {
    try {
      var usuario = usuarioRepositorio.findByEmail(usuarioEmail);
      var securityUser = new SegurancaUsuario(usuario.get());
      return securityUser;
    } catch (Exception e) {
      throw new AutenticacaoExcecao("Credenciais inválidas");
    }
  }
}
