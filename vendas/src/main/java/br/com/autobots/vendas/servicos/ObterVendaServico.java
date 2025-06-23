package br.com.autobots.vendas.servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.vendas.apis.UsuariosApi;
import br.com.autobots.vendas.apis.VeiculosApi;
import br.com.autobots.vendas.apis.MercadoriasApi;
import br.com.autobots.vendas.entidades.Venda;
import br.com.autobots.vendas.excecoes.NaoEncontradoExcecao;
import br.com.autobots.vendas.excecoes.UsuarioNaoAutorizadoExcecao;
import br.com.autobots.vendas.provedores.AutenticacaoProvedor;
import br.com.autobots.vendas.repositorios.VendaRepositorio;

@Service
public class ObterVendaServico {
  @Autowired
  private VendaRepositorio vendaRepositorio;

  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  @Autowired
  private UsuariosApi usuariosApi;

  @Autowired
  private MercadoriasApi mercadoriasApi;

  @Autowired
  private VeiculosApi veiculosApi;

  public Venda obterVenda(Long id, Long empresaId, String jwt) {
    Optional<Venda> venda = vendaRepositorio.findByIdAndEmpresaId(id, empresaId);
    if (venda.isEmpty()) {
      throw new NaoEncontradoExcecao("Venda não encontrada");
    }

    var perfil = autenticacaoProvedor.getPerfil();
    switch (perfil) {
      case CLIENTE:
        if (venda.get().getCliente().getEmail() != autenticacaoProvedor.getUsuario().getEmail()) {
          throw new UsuarioNaoAutorizadoExcecao();
        }
        break;
      case VENDEDOR:
        if (venda.get().getVendedor().getEmail() != autenticacaoProvedor.getUsuario().getEmail()) {
          throw new UsuarioNaoAutorizadoExcecao();
        }
        break;
      default:
        break;
    }

    var authorization = "Bearer " + jwt;

    var cliente = usuariosApi.obterUsuarioPorId(authorization, venda.get().getClienteId());
    var vendedor = usuariosApi.obterUsuarioPorId(authorization, venda.get().getVendedorId());
    venda.get().setCliente(cliente);
    venda.get().setVendedor(vendedor);

    var veiculo = veiculosApi.obterVeiculo(authorization, empresaId, venda.get().getVeiculoId());
    venda.get().setVeiculo(veiculo);

    for (Long pecaId : venda.get().getPecasIds()) {
      var peca = mercadoriasApi.obterPeca(authorization, empresaId, pecaId);
      venda.get().getPecas().add(peca);
    }
    for (Long servicoId : venda.get().getServicosIds()) {
      var servico = mercadoriasApi.obterServico(authorization, empresaId, servicoId);
      venda.get().getServicos().add(servico);
    }

    return venda.get();
  }
}
