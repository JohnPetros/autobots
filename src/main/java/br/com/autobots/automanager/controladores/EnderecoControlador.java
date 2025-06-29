package br.com.autobots.automanager.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.autobots.automanager.entidades.Endereco;
import br.com.autobots.automanager.repositorios.ClienteRepositorio;
import br.com.autobots.automanager.repositorios.EnderecoRepositorio;
import br.com.autobots.automanager.servicos.AdicionaLinkEnderecoServico;
import br.com.autobots.automanager.servicos.AtualizaEnderecoServico;

@RestController
@Tag(name = "Endereco", description = "CRUD de enderecos")
public class EnderecoControlador {
  @Autowired
  private EnderecoRepositorio enderecoRepositorio;

  @Autowired
  private ClienteRepositorio clienteRepositorio;

  @Autowired
  private AdicionaLinkEnderecoServico adicionaLinkEnderecoServico;

  @Autowired
  private AtualizaEnderecoServico atualizaEnderecoServico;

  @PostMapping("/endereco/cadastrar")
  @Operation(summary = "Cadastrar endereco", description = "Cadastra um novo endereco")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Endereco cadastrado com sucesso"),
      @ApiResponse(responseCode = "409", description = "Endereco já cadastrado"),
      @ApiResponse(responseCode = "400", description = "ID do cliente não pode ser nulo"),
      @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
  })
  public ResponseEntity<?> cadastrarEndereco(@RequestBody Endereco endereco) {
    if (endereco.getId() != null) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    if (endereco.getClienteId() == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    var cliente = clienteRepositorio.findById(endereco.getClienteId());
    if (cliente.isPresent()) {
      var clienteEntity = cliente.get();
      clienteEntity.setEndereco(endereco);
      enderecoRepositorio.save(endereco);
      clienteRepositorio.save(clienteEntity);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/enderecos")
  @Operation(summary = "Obter todos os enderecos", description = "Retorna uma lista de todos os enderecos cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Enderecos encontrados", content = @Content(schema = @Schema(implementation = List.class))),
      @ApiResponse(responseCode = "404", description = "Nenhum endereco cadastrado")
  })
  public ResponseEntity<List<Endereco>> obterEnderecos() {
    List<Endereco> enderecos = enderecoRepositorio.findAll();
    if (enderecos.isEmpty()) {
      ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkEnderecoServico.adicionarLink(enderecos);
      ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(enderecos, HttpStatus.OK);
      return resposta;
    }
  }

  @GetMapping("/endereco/{id}")
  @Operation(summary = "Obter endereco", description = "Retorna um endereco específico com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Endereco encontrado", content = @Content(schema = @Schema(implementation = Endereco.class))),
      @ApiResponse(responseCode = "404", description = "Endereco não encontrado")
  })
  public ResponseEntity<Endereco> obterEndereco(@PathVariable long id) {
    Optional<Endereco> cliente = enderecoRepositorio.findById(id);
    if (cliente.isEmpty()) {
      ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkEnderecoServico.adicionarLink(cliente.get());
      return ResponseEntity.status(HttpStatus.OK).body(cliente.get());
    }
  }

  @PutMapping("/endereco/atualizar")
  @Operation(summary = "Atualizar endereco", description = "Atualiza as informações de um endereco existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Endereco atualizado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Endereco não encontrado"),
      @ApiResponse(responseCode = "400", description = "ID do endereco não pode ser nulo")
  })
  public ResponseEntity<?> atualizarEndereco(@RequestBody Endereco enderecoAtualizado) {
    if (enderecoAtualizado.getId() == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Endereco> endereco = enderecoRepositorio.findById(enderecoAtualizado.getId());
    if (endereco.isPresent()) {
      atualizaEnderecoServico.atualizar(endereco.get(), enderecoAtualizado);
      enderecoRepositorio.save(endereco.get());
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/endereco/excluir")
  @Operation(summary = "Excluir endereco", description = "Exclui um endereco existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Endereco excluído com sucesso"),
      @ApiResponse(responseCode = "404", description = "Endereco não encontrado"),
      @ApiResponse(responseCode = "400", description = "ID do endereco não pode ser nulo")
  })
  public ResponseEntity<?> excluirEndereco(@RequestBody Endereco exclusao) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    Optional<Endereco> endereco = enderecoRepositorio.findById(exclusao.getId());
    if (endereco.isPresent()) {
      var cliente = clienteRepositorio.findByEndereco(endereco.get());
      if (cliente.isPresent()) {
        cliente.get().setEndereco(null);
        clienteRepositorio.save(cliente.get());
      }
      enderecoRepositorio.delete(endereco.get());
      status = HttpStatus.OK;
    }
    return new ResponseEntity<>(status);
  }

}
