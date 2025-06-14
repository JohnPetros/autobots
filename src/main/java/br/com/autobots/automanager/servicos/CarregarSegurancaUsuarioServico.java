package br.com.autobots.automanager.servicos;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.autobots.automanager.excecoes.AutenticacaoExcecao;
import br.com.autobots.automanager.repositorios.UsuarioRepositorio;
import br.com.autobots.automanager.seguranca.SegurancaUsuario;

@Service
public class CarregarSegurancaUsuarioServico implements UserDetailsService {
  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  @Override
  public UserDetails loadUserByUsername(String nomeUsuario) throws UsernameNotFoundException {
    try {
      var usuario = usuarioRepositorio.findByCredencialNomeUsuario(nomeUsuario);
      var securityUser = new SegurancaUsuario(usuario.get());
      return securityUser;
    } catch (Exception e) {
      throw new AutenticacaoExcecao("Credenciais inválidas");
    }
  }
}
