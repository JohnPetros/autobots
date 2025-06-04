package br.com.autobots.server.controladores;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.autobots.server.dtos.ClienteDto;
import br.com.autobots.server.entidades.Cliente;
import br.com.autobots.server.entidades.Documento;
import br.com.autobots.server.entidades.Endereco;
import br.com.autobots.server.entidades.Telefone;
import br.com.autobots.server.repositorios.ClienteRepositorio;
import br.com.autobots.server.servicos.AtualizaClienteServico;
import br.com.autobots.server.servicos.CadastraClienteServico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Cliente", description = "CRUD de clientes")
public class ClienteControlador {
  @Autowired
  private ClienteRepositorio repositorio;

  @Autowired
  private CadastraClienteServico cadastraClienteServico;

  @Autowired
  private AtualizaClienteServico atualizaClienteServico;

  @GetMapping("/cliente/{id}")
  @Operation(summary = "Obter cliente", description = "Retorna um cliente específico com base no ID fornecido")
  public Cliente obterCliente(@PathVariable long id) {
    var cliente = repositorio.findById(id);
    return cliente.get();
  }

  @GetMapping("/clientes")
  @Operation(summary = "Obter todos os clientes", description = "Retorna uma lista de todos os clientes cadastrados")
  public List<Cliente> obterClientes() {
    List<Cliente> clientes = repositorio.findAll();
    return clientes;
  }

  @PostMapping("/cadastro")
  @Operation(summary = "Cadastrar cliente", description = "Cadastra um novo cliente")
  public void cadastrarCliente(@RequestBody ClienteDto cliente) {
    var clienteEntity = cadastraClienteServico.cadastrar(cliente);
    repositorio.save(clienteEntity);
  }

  @PutMapping("/atualizar")
  @Operation(summary = "Atualizar cliente", description = "Atualiza as informações de um cliente existente")
  public void atualizarCliente(@RequestBody Cliente clienteAtualizado) {
    var cliente = repositorio.findById(clienteAtualizado.getId());
    atualizaClienteServico.atualizar(cliente.get(), clienteAtualizado);
    repositorio.save(cliente.get());
  }

  @DeleteMapping("/excluir")
  @Operation(summary = "Excluir cliente", description = "Exclui um cliente existente")
  public void excluirCliente(@RequestBody Cliente exclusao) {
    var cliente = repositorio.findById(exclusao.getId());
    repositorio.delete(cliente.get());
  }
}