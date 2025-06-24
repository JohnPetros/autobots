package br.com.autobots.vendas.servicos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.vendas.apis.SistemaApi;
import br.com.autobots.vendas.entidades.Venda;
import br.com.autobots.vendas.enums.PerfilUsuario;
import br.com.autobots.vendas.provedores.AutenticacaoProvedor;
import br.com.autobots.vendas.repositorios.VendaRepositorio;

@Service
public class ObterVendasServico {
  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  @Autowired
  private VendaRepositorio vendaRepositorio;

  @Autowired
  private SistemaApi sistemaApi;

  public List<Venda> obterVendas(Long empresaId, String token, LocalDate dataInicio, LocalDate dataFim) {
    var usuarioId = autenticacaoProvedor.getUsuario().getId();
    var perfil = autenticacaoProvedor.getPerfil();
    List<Venda> vendas = new ArrayList<>();

    if (perfil == PerfilUsuario.CLIENTE) {
      if (dataInicio != null && dataFim != null) {
        vendas = vendaRepositorio.findByClienteIdAndCadastroBetween(usuarioId, dataInicio, dataFim);
      } else {
        vendas = vendaRepositorio.findByClienteId(usuarioId);
      }
    } else if (perfil == PerfilUsuario.VENDEDOR) {
      if (dataInicio != null && dataFim != null) {
        vendas = vendaRepositorio.findByVendedorIdAndCadastroBetween(usuarioId, dataInicio, dataFim);
      } else {
        vendas = vendaRepositorio.findByVendedorId(usuarioId);
      }
    } else {
      if (dataInicio != null && dataFim != null) {
        vendas = vendaRepositorio.findAllByEmpresaIdAndCadastroBetween(empresaId, dataInicio, dataFim);
      } else {
        vendas = vendaRepositorio.findAllByEmpresaId(empresaId);
      }
    }

    var authorization = "Bearer " + token;

    vendas = vendas.stream().map(venda -> {
      return obterDadosDeApis(venda, authorization, empresaId);
    }).collect(Collectors.toList());

    return vendas;
  }

  private Venda obterDadosDeApis(Venda venda, String authorization, Long empresaId) {
    var cliente = sistemaApi.obterUsuarioPorId(authorization, venda.getClienteId());
    venda.setCliente(cliente);

    var vendedor = sistemaApi.obterUsuarioPorId(authorization, venda.getVendedorId());
    venda.setVendedor(vendedor);

    var veiculo = sistemaApi.obterVeiculo(authorization, empresaId, venda.getVeiculoId());
    venda.setVeiculo(veiculo);

    for (Long pecaId : venda.getPecasIds()) {
      var peca = sistemaApi.obterPeca(authorization, empresaId, pecaId);
      venda.getPecas().add(peca);
    }
    for (Long servicoId : venda.getServicosIds()) {
      var servico = sistemaApi.obterServico(authorization, empresaId, servicoId);
      venda.getServicos().add(servico);
    }

    return venda;
  }
}
