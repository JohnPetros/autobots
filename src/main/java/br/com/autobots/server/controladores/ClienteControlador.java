package br.com.autobots.server.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.autobots.server.entidades.Cliente;
import br.com.autobots.server.repositorios.ClienteRepositorio;
import br.com.autobots.server.servicos.AtualizaClienteServico;

@RestController
@RequestMapping("/cliente")
public class ClienteControlador {
  @Autowired
  private ClienteRepositorio repositorio;

  @Autowired
  private AtualizaClienteServico atualizaClienteServico;

  @GetMapping("/cliente/{id}")
  public Cliente obterCliente(@PathVariable long id) {
    var cliente = repositorio.findById(id);
    return cliente.get();
  }

  @GetMapping("/clientes")
  public List<Cliente> obterClientes() {
    List<Cliente> clientes = repositorio.findAll();
    return clientes;
  }

  @PostMapping("/cadastro")
  public void cadastrarCliente(@RequestBody Cliente cliente) {
    repositorio.save(cliente);
  }

  @PutMapping("/atualizar")
  public void atualizarCliente(@RequestBody Cliente clienteAtualizado) {
    var cliente = repositorio.findById(clienteAtualizado.getId());
    atualizaClienteServico.atualizar(cliente.get(), clienteAtualizado);
    repositorio.save(cliente.get());
  }

  @DeleteMapping("/excluir")
  public void excluirCliente(@RequestBody Cliente exclusao) {
    var cliente = repositorio.findById(exclusao.getId());
    repositorio.delete(cliente.get());
  }
}