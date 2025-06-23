package br.com.autobots.vendas.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.vendas.apis.MercadoriasApi;
import br.com.autobots.vendas.apis.UsuariosApi;
import br.com.autobots.vendas.apis.VeiculosApi;
import br.com.autobots.vendas.entidades.Venda;

@Service
public class ValidaVendaServico {
  @Autowired
  private UsuariosApi usuariosApi;

  @Autowired
  private MercadoriasApi mercadoriasApi;

  @Autowired
  private VeiculosApi veiculosApi;

  public void validar(Venda venda, String jwt) {
    if (venda.getClienteId() != null) {
      System.out.println("Cliente: " + venda.getClienteId());
      var cliente = usuariosApi.obterUsuarioPorId("Bearer " + jwt, venda.getClienteId());
      System.out.println("Cliente: " + cliente);
    }

    if (venda.getVendedorId() != null) {
      System.out.println("Vendedor: " + venda.getVendedorId());
      var vendedor = usuariosApi.obterUsuarioPorId("Bearer " + jwt, venda.getVendedorId());
      System.out.println("Vendedor: " + vendedor);
    }

    if (venda.getPecasIds() != null) {
      for (Long mercadoriaId : venda.getPecasIds()) {
        if (mercadoriaId != null) {
          mercadoriasApi.obterPeca("Bearer " + jwt, mercadoriaId, venda.getEmpresaId());
        }
      }
    }

    if (venda.getServicosIds() != null) {
      for (Long servicoId : venda.getServicosIds()) {
        if (servicoId != null) {
          mercadoriasApi.obterServico("Bearer " + jwt, servicoId, venda.getEmpresaId());
        }
      }

      if (venda.getVeiculoId() != null) {
        veiculosApi.obterVeiculo("Bearer " + jwt, venda.getVeiculoId(), venda.getEmpresaId());
      }
    }
  }
}
