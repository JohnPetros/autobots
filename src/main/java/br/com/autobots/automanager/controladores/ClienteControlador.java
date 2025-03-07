package br.com.autobots.automanager.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.autobots.automanager.entidades.Cliente;
import br.com.autobots.automanager.servicos.AdicionaLinkClienteServico;
import br.com.autobots.automanager.servicos.AtualizaClienteServico;
import br.com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
public class ClienteControlador {
  @Autowired
  private ClienteRepositorio repositorio;
  @Autowired
  private AdicionaLinkClienteServico adicionaLinkClienteServico;
  @Autowired
  private AtualizaClienteServico atualizaClienteServico;

  @GetMapping("/cliente/{id}")
  public ResponseEntity<Cliente> obterCliente(@PathVariable long id) {
    Optional<Cliente> cliente = repositorio.findById(id);
    if (cliente.isEmpty()) {
      ResponseEntity<Cliente> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkClienteServico.adicionarLink(cliente.get());
      return ResponseEntity.status(HttpStatus.OK).body(cliente.get());
    }
  }

  @GetMapping("/clientes")
  public ResponseEntity<List<Cliente>> obterClientes() {
    List<Cliente> clientes = repositorio.findAll();
    if (clientes.isEmpty()) {
      ResponseEntity<List<Cliente>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkClienteServico.adicionarLink(clientes);
      ResponseEntity<List<Cliente>> resposta = new ResponseEntity<>(clientes, HttpStatus.FOUND);
      return resposta;
    }
  }

  @PostMapping("/cliente/cadastro")
  public ResponseEntity<?> cadastrarCliente(@RequestBody Cliente cliente) {
    HttpStatus status = HttpStatus.CONFLICT;
    if (cliente.getId() == null) {
      repositorio.save(cliente);
      status = HttpStatus.CREATED;
    }
    return new ResponseEntity<>(status);

  }

  @PutMapping("/cliente/atualizar")
  public ResponseEntity<?> atualizarCliente(@RequestBody Cliente clienteAtualizacao) {
    HttpStatus status = HttpStatus.CONFLICT;
    Optional<Cliente> cliente = repositorio.findById(clienteAtualizacao.getId());
    if (cliente.isEmpty()) {
      atualizaClienteServico.atualizar(cliente.get(), clienteAtualizacao);
      repositorio.save(cliente.get());
      status = HttpStatus.OK;
    } else {
      status = HttpStatus.BAD_REQUEST;
    }
    return new ResponseEntity<>(status);
  }

  @DeleteMapping("/cliente/excluir")
  public ResponseEntity<?> excluirCliente(@RequestBody Cliente exclusao) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    Optional<Cliente> cliente = repositorio.findById(exclusao.getId());
    if (cliente.isPresent()) {
      repositorio.delete(cliente.get());
      status = HttpStatus.OK;
    }
    return new ResponseEntity<>(status);
  }
}