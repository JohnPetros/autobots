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
import br.com.autobots.mercadorias.entidades.Servico;
import br.com.autobots.mercadorias.excecoes.NaoEncontradoExcecao;
import br.com.autobots.mercadorias.repositorios.ServicoRepositorio;
import br.com.autobots.mercadorias.servicos.AdicionaLinkServicoServico;
import br.com.autobots.mercadorias.servicos.AtualizaServicoServico;

@RestController
@Tag(name = "Servico", description = "CRUD de servicos")
public class ServicoControlador {
  @Autowired
  private ServicoRepositorio servicoRepositorio;

  @Autowired
  private AdicionaLinkServicoServico adicionaLinkServicoServico;

  @Autowired
  private AtualizaServicoServico atualizaServicoServico;

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @PostMapping("/{empresaId}/servico/cadastrar")
  @Operation(summary = "Cadastrar servico", description = "Cadastra um novo servico")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Servico cadastrado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
      @ApiResponse(responseCode = "409", description = "Servico já cadastrado")
  })
  public ResponseEntity<?> cadastrarServico(@RequestBody @Valid Servico servico, @PathVariable long empresaId) {
    servico.setEmpresaId(empresaId);
    servicoRepositorio.save(servico);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
  @GetMapping("/{empresaId}/servicos")
  @Operation(summary = "Obter todos os servicos", description = "Retorna uma lista de todos os servicos cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Servicos encontrados", content = @Content(schema = @Schema(implementation = List.class))),
      @ApiResponse(responseCode = "404", description = "Nenhum servico cadastrado")
  })
  public ResponseEntity<List<Servico>> obterServicos(@PathVariable long empresaId) {
    List<Servico> servicos = servicoRepositorio.findByEmpresaId(empresaId);
    if (servicos.isEmpty()) {
      throw new NaoEncontradoExcecao("Nenhum servico cadastrado");
    } else {
      adicionaLinkServicoServico.adicionarLink(servicos, empresaId);
      ResponseEntity<List<Servico>> resposta = new ResponseEntity<>(servicos, HttpStatus.OK);
      return resposta;
    }
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
  @GetMapping("/{empresaId}/servico/{id}")
  @Operation(summary = "Obter servico", description = "Retorna um servico específico com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Servico encontrado", content = @Content(schema = @Schema(implementation = Servico.class))),
      @ApiResponse(responseCode = "404", description = "Servico não encontrado")
  })
  public ResponseEntity<Servico> obterServico(@PathVariable long id, @PathVariable long empresaId) {
    Optional<Servico> servico = servicoRepositorio.findById(id);
    if (servico.isEmpty()) {
      throw new NaoEncontradoExcecao("Servico não encontrado");
    }
    adicionaLinkServicoServico.adicionarLink(servico.get(), empresaId);
    return ResponseEntity.status(HttpStatus.OK).body(servico.get());
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @PutMapping("/{empresaId}/servico/atualizar")
  @Operation(summary = "Atualizar servico", description = "Atualiza as informações de um servico existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Servico atualizado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Servico não encontrado"),
      @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
  })
  public ResponseEntity<?> atualizarServico(
      @RequestBody Servico servicoAtualizado,
      @PathVariable long empresaId) {
    Optional<Servico> servico = servicoRepositorio.findById(servicoAtualizado.getId());
    if (servico.isEmpty()) {
      throw new NaoEncontradoExcecao("Servico não encontrado");
    }
    atualizaServicoServico.atualizar(servico.get(), servicoAtualizado);
    servicoRepositorio.save(servico.get());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @DeleteMapping("/{empresaId}/servico/excluir")
  @Operation(summary = "Excluir servico", description = "Exclui um servico existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Servico excluído com sucesso"),
      @ApiResponse(responseCode = "404", description = "Servico não encontrado")
  })
  public ResponseEntity<?> excluirServico(
      @RequestBody Servico exclusao,
      @PathVariable long empresaId) {
    Optional<Servico> servico = servicoRepositorio.findById(exclusao.getId());
    if (servico.isEmpty()) {
      throw new NaoEncontradoExcecao("Servico não encontrado");
    }
    servicoRepositorio.delete(servico.get());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
