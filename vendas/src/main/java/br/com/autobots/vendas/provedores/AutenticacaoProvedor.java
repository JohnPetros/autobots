package br.com.autobots.vendas.provedores;

import org.springframework.stereotype.Component;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.autobots.vendas.entidades.Usuario;
import br.com.autobots.vendas.enums.PerfilUsuario;
import br.com.autobots.vendas.excecoes.AutenticacaoExcecao;
import br.com.autobots.vendas.seguranca.SegurancaUsuario;

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
