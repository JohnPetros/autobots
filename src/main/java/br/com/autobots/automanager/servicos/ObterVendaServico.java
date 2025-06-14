package br.com.autobots.automanager.servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Venda;
import br.com.autobots.automanager.excecoes.NaoEncontradoExcecao;
import br.com.autobots.automanager.excecoes.UsuarioNaoAutorizadoExcecao;
import br.com.autobots.automanager.provedores.AutenticacaoProvedor;
import br.com.autobots.automanager.repositorios.EmpresaRepositorio;
import br.com.autobots.automanager.repositorios.VendaRepositorio;

@Service
public class ObterVendaServico {
  @Autowired
  private VendaRepositorio vendaRepositorio;

  @Autowired
  private EmpresaRepositorio empresaRepositorio;

  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  public Venda obterVenda(Long id, Long empresaId) {
    var empresa = empresaRepositorio.findById(empresaId);
    if (empresa.isEmpty()) {
      throw new NaoEncontradoExcecao("Empresa não encontrada");
    }

    Optional<Venda> venda = vendaRepositorio.findById(id);
    if (venda.isEmpty()) {
      throw new NaoEncontradoExcecao("Venda não encontrada");
    }

    var perfil = autenticacaoProvedor.getPerfil();
    switch (perfil) {
      case CLIENTE:
        if (venda.get().getCliente().getId() != autenticacaoProvedor.getUsuario().getId()) {
          throw new UsuarioNaoAutorizadoExcecao();
        }
        break;
      case VENDEDOR:
        if (venda.get().getVendedor().getId() != autenticacaoProvedor.getUsuario().getId()) {
          throw new UsuarioNaoAutorizadoExcecao();
        }
        break;
      default:
        break;
    }

    return venda.get();
  }
}
