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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@Tag(name = "Clientes", description = "CRUD de clientes")
public class ClienteControlador {
  @Autowired
  private ClienteRepositorio repositorio;

  @Autowired
  private AdicionaLinkClienteServico adicionaLinkClienteServico;

  @Autowired
  private AtualizaClienteServico atualizaClienteServico;

  @GetMapping("/cliente/{id}")
  @Operation(summary = "Obter cliente", description = "Retorna um cliente específico com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Cliente encontrado", content = @Content(schema = @Schema(implementation = Cliente.class))),
      @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
  })
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
  @Operation(summary = "Obter todos os clientes", description = "Retorna uma lista de todos os clientes cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Clientes encontrados", content = @Content(schema = @Schema(implementation = List.class))),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente cadastrado")
  })
  public ResponseEntity<List<Cliente>> obterClientes() {
    List<Cliente> clientes = repositorio.findAll();
    if (clientes.isEmpty()) {
      ResponseEntity<List<Cliente>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkClienteServico.adicionarLink(clientes);
      ResponseEntity<List<Cliente>> resposta = new ResponseEntity<>(clientes, HttpStatus.OK);
      return resposta;
    }
  }

  @PostMapping("/cliente/cadastrar")
  @Operation(summary = "Cadastrar cliente", description = "Cadastra um novo cliente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso"),
      @ApiResponse(responseCode = "409", description = "Cliente já cadastrado")
  })
  public ResponseEntity<?> cadastrarCliente(@RequestBody Cliente cliente) {
    HttpStatus status = HttpStatus.CONFLICT;
    Optional<Cliente> clienteExistente = repositorio.findById(cliente.getId());
    if (clienteExistente.isEmpty()) {
      repositorio.save(cliente);
      status = HttpStatus.CREATED;
    }
    return new ResponseEntity<>(status);
  }

  @PutMapping("/cliente/atualizar")
  @Operation(summary = "Atualizar cliente", description = "Atualiza as informações de um cliente existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
      @ApiResponse(responseCode = "400", description = "Cliente não encontrado")
  })
  public ResponseEntity<?> atualizarCliente(@RequestBody Cliente clienteAtualizacao) {
    HttpStatus status = HttpStatus.CONFLICT;
    Optional<Cliente> cliente = repositorio.findById(clienteAtualizacao.getId());
    if (cliente.isPresent()) {
      atualizaClienteServico.atualizar(cliente.get(), clienteAtualizacao);
      repositorio.save(cliente.get());
      status = HttpStatus.OK;
    } else {
      status = HttpStatus.BAD_REQUEST;
    }
    return new ResponseEntity<>(status);
  }

  @DeleteMapping("/cliente/excluir")
  @Operation(summary = "Excluir cliente", description = "Exclui um cliente existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Cliente excluído com sucesso"),
      @ApiResponse(responseCode = "400", description = "Cliente não encontrado")
  })
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