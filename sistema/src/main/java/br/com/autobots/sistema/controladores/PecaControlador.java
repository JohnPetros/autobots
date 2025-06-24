package br.com.autobots.sistema.controladores;

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
import br.com.autobots.sistema.entidades.Empresa;
import br.com.autobots.sistema.entidades.Peca;
import br.com.autobots.sistema.excecoes.NaoEncontradoExcecao;
import br.com.autobots.sistema.repositorios.PecaRepositorio;
import br.com.autobots.sistema.servicos.AdicionaLinkPecaServico;
import br.com.autobots.sistema.servicos.AtualizaPecaServico;
import br.com.autobots.sistema.repositorios.EmpresaRepositorio;

@RestController
@Tag(name = "Peca", description = "CRUD de pecas")
public class PecaControlador {
  @Autowired
  private PecaRepositorio pecaRepositorio;

  @Autowired
  private AdicionaLinkPecaServico adicionaLinkPecaServico;

  @Autowired
  private AtualizaPecaServico atualizaPecaServico;

  @Autowired
  private EmpresaRepositorio empresaRepositorio;

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @PostMapping("/{empresaId}/peca/cadastrar")
  @Operation(summary = "Cadastrar telefone", description = "Cadastra um novo telefone")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Telefone cadastrado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
      @ApiResponse(responseCode = "409", description = "Telefone já cadastrado")
  })
  public ResponseEntity<?> cadastrarPeca(
      @RequestBody @Valid Peca peca,
      @PathVariable long empresaId) {
    Optional<Empresa> empresa = empresaRepositorio.findById(empresaId);
    if (empresa.isEmpty()) {
      throw new NaoEncontradoExcecao("Empresa não encontrada");
    }
    pecaRepositorio.save(peca);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
  @GetMapping("/{empresaId}/pecas")
  @Operation(summary = "Obter todos os pecas", description = "Retorna uma lista de todos os pecas cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Pecas encontradas", content = @Content(schema = @Schema(implementation = List.class))),
      @ApiResponse(responseCode = "404", description = "Nenhum peca cadastrado")
  })
  public ResponseEntity<List<Peca>> obterPecas(@PathVariable long empresaId) {
    Optional<Empresa> empresa = empresaRepositorio.findById(empresaId);
    if (empresa.isEmpty()) {
      throw new NaoEncontradoExcecao("Empresa não encontrada");
    }
    List<Peca> pecas = pecaRepositorio.findByEmpresaId(empresaId);
    if (pecas.isEmpty()) {
      throw new NaoEncontradoExcecao("Nenhum peca cadastrada");
    } else {
      adicionaLinkPecaServico.adicionarLink(pecas, empresaId);
      ResponseEntity<List<Peca>> resposta = new ResponseEntity<>(pecas, HttpStatus.OK);
      return resposta;
    }
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
  @GetMapping("/{empresaId}/peca/{id}")
  @Operation(summary = "Obter peca", description = "Retorna um peca específico com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Peca encontrada", content = @Content(schema = @Schema(implementation = Peca.class))),
      @ApiResponse(responseCode = "404", description = "Peca não encontrada")
  })
  public ResponseEntity<Peca> obterPeca(@PathVariable long id, @PathVariable long empresaId) {
    Optional<Empresa> empresa = empresaRepositorio.findById(empresaId);
    if (empresa.isEmpty()) {
      throw new NaoEncontradoExcecao("Empresa não encontrada");
    }
    Optional<Peca> peca = pecaRepositorio.findById(id);
    if (peca.isEmpty()) {
      throw new NaoEncontradoExcecao("Peca não encontrada");
    }
    adicionaLinkPecaServico.adicionarLink(peca.get(), empresaId);
    return ResponseEntity.status(HttpStatus.OK).body(peca.get());
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @PutMapping("/{empresaId}/peca/atualizar")
  @Operation(summary = "Atualizar peca", description = "Atualiza as informações de um peca existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Peca atualizado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Peca não encontrada"),
      @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
  })
  public ResponseEntity<?> atualizarPeca(
      @RequestBody Peca pecaAtualizado,
      @PathVariable long empresaId) {
    Optional<Empresa> empresa = empresaRepositorio.findById(empresaId);
    if (empresa.isEmpty()) {
      throw new NaoEncontradoExcecao("Empresa não encontrada");
    }
    Optional<Peca> peca = pecaRepositorio.findById(pecaAtualizado.getId());
    if (peca.isEmpty()) {
      throw new NaoEncontradoExcecao("Peca não encontrada");
    }
    atualizaPecaServico.atualizar(peca.get(), pecaAtualizado);
    pecaRepositorio.save(peca.get());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @DeleteMapping("/{empresaId}/peca/excluir")
  @Operation(summary = "Excluir peca", description = "Exclui um peca existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Peca excluído com sucesso"),
      @ApiResponse(responseCode = "404", description = "Peca não encontrada")
  })
  public ResponseEntity<?> excluirPeca(
      @RequestBody Peca exclusao,
      @PathVariable long empresaId) {
    Optional<Empresa> empresa = empresaRepositorio.findById(empresaId);
    if (empresa.isEmpty()) {
      throw new NaoEncontradoExcecao("Empresa não encontrada");
    }
    Optional<Peca> peca = pecaRepositorio.findById(exclusao.getId());
    if (peca.isEmpty()) {
      throw new NaoEncontradoExcecao("Peca não encontrada");
    }
    pecaRepositorio.delete(peca.get());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
