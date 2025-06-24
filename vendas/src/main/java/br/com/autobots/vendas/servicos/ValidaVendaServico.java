package br.com.autobots.vendas.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.vendas.apis.SistemaApi;
import br.com.autobots.vendas.entidades.Venda;

@Service
public class ValidaVendaServico {
  @Autowired
  private SistemaApi sistemaApi;

  public void validar(Venda venda, String jwt) {
    if (venda.getClienteId() != null) {
      System.out.println("Cliente: " + venda.getClienteId());
      var cliente = sistemaApi.obterUsuarioPorId("Bearer " + jwt, venda.getClienteId());
      System.out.println("Cliente: " + cliente);
    }

    if (venda.getVendedorId() != null) {
      System.out.println("Vendedor: " + venda.getVendedorId());
      var vendedor = sistemaApi.obterUsuarioPorId("Bearer " + jwt, venda.getVendedorId());
      System.out.println("Vendedor: " + vendedor);
    }

    if (venda.getPecasIds() != null) {
      for (Long mercadoriaId : venda.getPecasIds()) {
        if (mercadoriaId != null) {
          sistemaApi.obterPeca("Bearer " + jwt, mercadoriaId, venda.getEmpresaId());
        }
      }
    }

    if (venda.getServicosIds() != null) {
      for (Long servicoId : venda.getServicosIds()) {
        if (servicoId != null) {
          sistemaApi.obterServico("Bearer " + jwt, servicoId, venda.getEmpresaId());
        }
      }

      if (venda.getVeiculoId() != null) {
        sistemaApi.obterVeiculo("Bearer " + jwt, venda.getVeiculoId(), venda.getEmpresaId());
      }
    }
  }
}
