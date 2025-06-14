package br.com.autobots.automanager.servicos;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Mercadoria;
import br.com.autobots.automanager.entidades.Servico;
import br.com.autobots.automanager.entidades.Venda;
import br.com.autobots.automanager.enums.PerfilUsuario;
import br.com.autobots.automanager.excecoes.NaoEncontradoExcecao;
import br.com.autobots.automanager.repositorios.MercadoriaRepositorio;
import br.com.autobots.automanager.repositorios.ServicoRepositorio;
import br.com.autobots.automanager.repositorios.UsuarioRepositorio;
import br.com.autobots.automanager.repositorios.VeiculoRepositorio;

@Service
public class ValidaVendaServico {
  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  @Autowired
  private MercadoriaRepositorio mercadoriaRepositorio;

  @Autowired
  private ServicoRepositorio servicoRepositorio;

  @Autowired
  private VeiculoRepositorio veiculoRepositorio;

  public void validar(Venda venda) {
    if (venda.getCliente() != null) {
      var cliente = usuarioRepositorio.findByIdAndPerfil(venda.getCliente().getId(), PerfilUsuario.CLIENTE);
      if (cliente.isEmpty()) {
        throw new NaoEncontradoExcecao("Cliente não encontrado");
      }
      venda.setCliente(cliente.get());
    }

    if (venda.getVendedor() != null) {
      var vendedor = usuarioRepositorio.findByIdAndPerfil(venda.getVendedor().getId(), PerfilUsuario.VENDEDOR);
      if (vendedor.isEmpty()) {
        throw new NaoEncontradoExcecao("Vendedor não encontrado");
      }
      venda.setVendedor(vendedor.get());
    }

    if (venda.getMercadorias() != null) {
      var mercadorias = new ArrayList<Mercadoria>();
      for (Mercadoria mercadoria : venda.getMercadorias()) {
        if (mercadoria.getId() != null) {
          var mercadoriaExistente = mercadoriaRepositorio.findById(mercadoria.getId());
          if (mercadoriaExistente.isEmpty()) {
            throw new NaoEncontradoExcecao("Mercadoria não encontrada");
          }
          mercadorias.add(mercadoriaExistente.get());
        }
      }
      venda.setMercadorias(mercadorias);
    }

    if (venda.getServicos() != null) {
      var servicos = new ArrayList<Servico>();
      for (Servico servico : venda.getServicos()) {
        if (servico.getId() != null) {
          var servicoExistente = servicoRepositorio.findById(servico.getId());
          if (servicoExistente.isEmpty()) {
            throw new NaoEncontradoExcecao("Serviço não encontrado");
          }
          servicos.add(servicoExistente.get());
        }
      }
      venda.setServicos(servicos);
    }

    if (venda.getVeiculo() != null) {
      var veiculo = veiculoRepositorio.findById(venda.getVeiculo().getId());
      if (veiculo.isEmpty()) {
        throw new NaoEncontradoExcecao("Veículo não encontrado");
      }
      venda.setVeiculo(veiculo.get());
    }
  }

}
