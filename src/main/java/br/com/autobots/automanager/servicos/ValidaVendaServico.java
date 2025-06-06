package br.com.autobots.automanager.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Mercadoria;
import br.com.autobots.automanager.entidades.Servico;
import br.com.autobots.automanager.entidades.Venda;
import br.com.autobots.automanager.repositorios.MercadoriaRepositorio;
import br.com.autobots.automanager.repositorios.ServicoRepositorio;
import br.com.autobots.automanager.repositorios.UsuarioRepositorio;

@Service
public class ValidaVendaServico {
  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  @Autowired
  private MercadoriaRepositorio mercadoriaRepositorio;

  @Autowired
  private ServicoRepositorio servicoRepositorio;

  public boolean validar(Venda venda) {
    var cliente = usuarioRepositorio.findById(venda.getCliente().getId());
    System.out.println("usuarioExistente: " + usuarioRepositorio);
    if (cliente.isEmpty()) {
      return false;
    }

    for (Mercadoria mercadoria : venda.getMercadorias()) {
      var mercadoriaExistente = mercadoriaRepositorio.findById(mercadoria.getId());
      System.out.println("mercadoriaExistente: " + mercadoriaExistente);
      if (mercadoriaExistente.isEmpty()) {
        return false;
      }
    }

    for (Servico servico : venda.getServicos()) {
      var servicoExistente = servicoRepositorio.findById(servico.getId());
      System.out.println("servicoExistente: " + servicoExistente);
      if (servicoExistente.isEmpty()) {
        return false;
      }
    }

    return true;
  }
}
