package br.com.autobots.sistema.provedores;

import org.springframework.stereotype.Component;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.autobots.sistema.entidades.Usuario;
import br.com.autobots.sistema.enums.PerfilUsuario;
import br.com.autobots.sistema.excecoes.AutenticacaoExcecao;
import br.com.autobots.sistema.seguranca.SegurancaUsuario;

@Component
public class AutenticacaoProvedor {
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
}
