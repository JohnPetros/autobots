package br.com.autobots.mercadorias.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import br.com.autobots.mercadorias.entidades.Peca;
import br.com.autobots.mercadorias.excecoes.NaoEncontradoExcecao;
import br.com.autobots.mercadorias.repositorios.PecaRepositorio;
import br.com.autobots.mercadorias.servicos.AdicionaLinkPecaServico;
import br.com.autobots.mercadorias.servicos.AtualizaPecaServico;

@RestController
@Tag(name = "Peca", description = "CRUD de peças")
public class PecaControlador {
  @Autowired
  private PecaRepositorio pecaRepositorio;

  @Autowired
  private AdicionaLinkPecaServico adicionaLinkPecaServico;

  @Autowired
  private AtualizaPecaServico atualizaPecaServico;

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @PostMapping("/{empresaId}/peca/cadastrar")
  @Operation(summary = "Cadastrar peça", description = "Cadastra uma nova peça")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Peça cadastrada com sucesso"),
  })
  public ResponseEntity<?> cadastrarPeca(
      @RequestBody @Valid Peca peca,
      @PathVariable long empresaId) {
    peca.setEmpresaId(empresaId);
    pecaRepositorio.save(peca);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
  @GetMapping("/{empresaId}/pecas")
  @Operation(summary = "Obter todas as peças", description = "Retorna uma lista de todas as peças cadastradas")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Peças encontradas", content = @Content(schema = @Schema(implementation = List.class))),
  })
  public ResponseEntity<List<Peca>> obterPecas(@PathVariable long empresaId) {
    List<Peca> pecas = pecaRepositorio.findByEmpresaId(empresaId);
    if (pecas.isEmpty()) {
      throw new NaoEncontradoExcecao("Nenhuma peça cadastrada");
    } else {
      adicionaLinkPecaServico.adicionarLink(pecas, empresaId);
      ResponseEntity<List<Peca>> resposta = new ResponseEntity<>(pecas, HttpStatus.OK);
      return resposta;
    }
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
  @GetMapping("/{empresaId}/peca/{id}")
  @Operation(summary = "Obter peça", description = "Retorna uma peça específica com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Peça encontrada", content = @Content(schema = @Schema(implementation = Peca.class))),
  })
  public ResponseEntity<Peca> obterPeca(@PathVariable long id, @PathVariable long empresaId) {
    Optional<Peca> peca = pecaRepositorio.findById(id);
    if (peca.isEmpty()) {
      throw new NaoEncontradoExcecao("Peça não encontrada");
    }
    adicionaLinkPecaServico.adicionarLink(peca.get(), empresaId);
    return ResponseEntity.status(HttpStatus.OK).body(peca.get());
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @PutMapping("/{empresaId}/peca/atualizar")
  @Operation(summary = "Atualizar peça", description = "Atualiza as informações de uma peça existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Peça atualizado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Peça não encontrada"),
  })
  public ResponseEntity<?> atualizarPeca(
      @RequestBody Peca pecaAtualizado,
      @PathVariable long empresaId) {
    Optional<Peca> peca = pecaRepositorio.findById(pecaAtualizado.getId());
    if (peca.isEmpty()) {
      throw new NaoEncontradoExcecao("Peça não encontrada");
    }
    atualizaPecaServico.atualizar(peca.get(), pecaAtualizado);
    pecaRepositorio.save(peca.get());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @DeleteMapping("/{empresaId}/peca/excluir")
  @Operation(summary = "Excluir peça", description = "Exclui uma peça existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Peça excluída com sucesso"),
      @ApiResponse(responseCode = "404", description = "Peça não encontrada")
  })
  public ResponseEntity<?> excluirPeca(
      @RequestBody Peca exclusao,
      @PathVariable long empresaId) {
    Optional<Peca> peca = pecaRepositorio.findById(exclusao.getId());
    if (peca.isEmpty()) {
      throw new NaoEncontradoExcecao("Peça não encontrada");
    }
    pecaRepositorio.delete(peca.get());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
