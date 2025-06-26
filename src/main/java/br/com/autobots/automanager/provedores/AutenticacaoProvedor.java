package br.com.autobots.automanager.provedores;

import org.springframework.stereotype.Component;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.autobots.automanager.entidades.Usuario;
import br.com.autobots.automanager.enums.PerfilUsuario;
import br.com.autobots.automanager.excecoes.AutenticacaoExcecao;
import br.com.autobots.automanager.seguranca.SegurancaUsuario;

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

  public Long getUsuarioId() {
    var usuario = getUsuario();
    return usuario.getId();
  }

}
