package br.com.autobots.server.servicos;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.autobots.server.entidades.Cliente;

@Service
public class SelectionaClienteServico {
  public Cliente selecionar(List<Cliente> clientes, long id) {
    Cliente selecionado = null;
    for (Cliente cliente : clientes) {
      if (cliente.getId() == id) {
        selecionado = cliente;
      }
    }
    return selecionado;
  }
}
