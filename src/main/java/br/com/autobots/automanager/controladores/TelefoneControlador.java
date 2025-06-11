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

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import br.com.autobots.automanager.entidades.Telefone;
import br.com.autobots.automanager.excecoes.NaoEncontradoExcecao;
import br.com.autobots.automanager.repositorios.TelefoneRepositorio;
import br.com.autobots.automanager.servicos.AdicionaLinkTelefoneServico;
import br.com.autobots.automanager.servicos.AtualizaTelefoneServico;
import br.com.autobots.automanager.servicos.ValidaTelefoneServico;

@RestController
@Tag(name = "Telefone", description = "CRUD de telefones")
public class TelefoneControlador {
  @Autowired
  private TelefoneRepositorio repositorio;

  @Autowired
  private AdicionaLinkTelefoneServico adicionaLinkTelefoneServico;

  @Autowired
  private AtualizaTelefoneServico atualizaTelefoneServico;

  @Autowired
  private ValidaTelefoneServico validaTelefoneServico;

  @PostMapping("/telefone/cadastrar")
  @Operation(summary = "Cadastrar telefone", description = "Cadastra um novo telefone")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Telefone cadastrado com sucesso"),
      @ApiResponse(responseCode = "409", description = "Telefone com DDD e número já cadastrado")
  })
  public ResponseEntity<?> cadastrarTelefone(@RequestBody @Valid Telefone telefone) {
    validaTelefoneServico.validar(telefone);
    repositorio.save(telefone);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/telefones")
  @Operation(summary = "Obter todos os telefones", description = "Retorna uma lista de todos os telefones cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Telefones encontrados", content = @Content(schema = @Schema(implementation = List.class))),
      @ApiResponse(responseCode = "404", description = "Nenhum telefone cadastrado")
  })
  public ResponseEntity<List<Telefone>> obterTelefones() {
    List<Telefone> telefones = repositorio.findAll();
    if (telefones.isEmpty()) {
      throw new NaoEncontradoExcecao("Nenhum telefone cadastrado");
    } else {
      adicionaLinkTelefoneServico.adicionarLink(telefones);
      ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(telefones, HttpStatus.OK);
      return resposta;
    }
  }

  @GetMapping("/telefone/{id}")
  @Operation(summary = "Obter telefone", description = "Retorna um telefone específico com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Telefone encontrado", content = @Content(schema = @Schema(implementation = Telefone.class))),
      @ApiResponse(responseCode = "404", description = "Telefone não encontrado")
  })
  public ResponseEntity<Telefone> obterTelefone(@PathVariable long id) {
    Optional<Telefone> cliente = repositorio.findById(id);
    if (cliente.isEmpty()) {
      ResponseEntity<Telefone> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkTelefoneServico.adicionarLink(cliente.get());
      return ResponseEntity.status(HttpStatus.OK).body(cliente.get());
    }
  }

  @PutMapping("/telefone/atualizar")
  @Operation(summary = "Atualizar telefone", description = "Atualiza as informações de um telefone existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Telefone atualizado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Telefone não encontrado")
  })
  public ResponseEntity<?> atualizarTelefone(@RequestBody Telefone telefoneAtualizado) {
    Optional<Telefone> telefone = repositorio.findById(telefoneAtualizado.getId());
    if (telefone.isEmpty()) {
      throw new NaoEncontradoExcecao("Telefone não encontrado");
    }
    validaTelefoneServico.validar(telefoneAtualizado);
    atualizaTelefoneServico.atualizar(telefone.get(), telefoneAtualizado);
    repositorio.save(telefone.get());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/telefone/excluir")
  @Operation(summary = "Excluir telefone", description = "Exclui um telefone existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Telefone excluído com sucesso"),
      @ApiResponse(responseCode = "404", description = "Telefone não encontrado")
  })
  public ResponseEntity<?> excluirTelefone(@RequestBody Telefone exclusao) {
    Optional<Telefone> telefone = repositorio.findById(exclusao.getId());
    if (telefone.isEmpty()) {
      throw new NaoEncontradoExcecao("Telefone não encontrado");
    }
    repositorio.delete(telefone.get());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}