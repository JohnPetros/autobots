package br.com.autobots.automanager.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import br.com.autobots.automanager.excecoes.NaoEncontradoExcecao;
import br.com.autobots.automanager.repositorios.EnderecoRepositorio;
import br.com.autobots.automanager.servicos.AdicionaLinkEnderecoServico;
import br.com.autobots.automanager.servicos.AtualizaEnderecoServico;

@RestController
@Tag(name = "Endereco", description = "CRUD de enderecos")
public class EnderecoControlador {
  @Autowired
  private EnderecoRepositorio enderecoRepositorio;

  @Autowired
  private AdicionaLinkEnderecoServico adicionaLinkEnderecoServico;

  @Autowired
  private AtualizaEnderecoServico atualizaEnderecoServico;

  @GetMapping("/enderecos")
  @Operation(summary = "Obter todos os enderecos", description = "Retorna uma lista de todos os enderecos cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Enderecos encontrados", content = @Content(schema = @Schema(implementation = List.class))),
      @ApiResponse(responseCode = "404", description = "Nenhum endereco cadastrado")
  })
  public ResponseEntity<List<Endereco>> obterEnderecos() {
    List<Endereco> enderecos = enderecoRepositorio.findAll();
    if (enderecos.isEmpty()) {
      throw new NaoEncontradoExcecao("Nenhum endereco cadastrado");
    } else {
      adicionaLinkEnderecoServico.adicionarLink(enderecos, null);
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
    Optional<Endereco> endereco = enderecoRepositorio.findById(id);
    if (endereco.isEmpty()) {
      ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkEnderecoServico.adicionarLink(endereco.get(), null);
      return ResponseEntity.status(HttpStatus.OK).body(endereco.get());
    }
  }

  @PutMapping("/endereco/atualizar")
  @Operation(summary = "Atualizar endereco", description = "Atualiza as informações de um endereco existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Endereco atualizado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Endereco não encontrado")
  })
  public ResponseEntity<?> atualizarEndereco(@RequestBody Endereco enderecoAtualizado) {
    Optional<Endereco> endereco = enderecoRepositorio.findById(enderecoAtualizado.getId());
    if (endereco.isEmpty()) {
      throw new NaoEncontradoExcecao("Endereco não encontrado");
    }
    atualizaEnderecoServico.atualizar(endereco.get(), enderecoAtualizado);
    enderecoRepositorio.save(endereco.get());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}