package br.com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Venda;
import br.com.autobots.automanager.enums.PerfilUsuario;
import br.com.autobots.automanager.excecoes.NaoEncontradoExcecao;
import br.com.autobots.automanager.provedores.AutenticacaoProvedor;
import br.com.autobots.automanager.repositorios.EmpresaRepositorio;

@Service
public class ObterVendasServico {
  @Autowired
  private EmpresaRepositorio empresaRepositorio;

  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  public List<Venda> obterVendas(Long empresaId) {
    var empresa = empresaRepositorio.findById(empresaId);
    if (empresa.isEmpty()) {
      throw new NaoEncontradoExcecao("Empresa não encontrada");
    }

    var perfil = autenticacaoProvedor.getPerfil();
    if (perfil == PerfilUsuario.CLIENTE) {
      var vendas = empresa.get().obterVendasPorCliente(autenticacaoProvedor.getUsuario());
      return vendas;
    }

    if (perfil == PerfilUsuario.VENDEDOR) {
      var vendas = empresa.get().obterVendasPorVendedor(autenticacaoProvedor.getUsuario());
      return vendas;
    }

    return empresa.get().getVendas();
  }
}
