package br.com.autobots.veiculos.controladores;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import br.com.autobots.veiculos.apis.UsuariosApi;
import br.com.autobots.veiculos.entidades.Veiculo;
import br.com.autobots.veiculos.excecoes.NaoEncontradoExcecao;
import br.com.autobots.veiculos.repositorios.VeiculoRepositorio;
import br.com.autobots.veiculos.servicos.AdicionaLinkVeiculoServico;
import br.com.autobots.veiculos.servicos.AtualizaVeiculoServico;
import br.com.autobots.veiculos.servicos.ValidaVeiculoServico;

@RestController
@Tag(name = "Veiculo", description = "CRUD de veiculos")
public class VeiculoControlador {
  @Autowired
  private VeiculoRepositorio veiculoRepositorio;

  @Autowired
  private AdicionaLinkVeiculoServico adicionaLinkVeiculoServico;

  @Autowired
  private AtualizaVeiculoServico atualizaVeiculoServico;

  @Autowired
  private ValidaVeiculoServico validaVeiculoServico;

  @Autowired
  private UsuariosApi usuariosApi;

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @PostMapping("/{empresaId}/veiculo/cadastrar")
  @Operation(summary = "Cadastrar veiculo", description = "Cadastra um novo veiculo")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Veiculo cadastrado com sucesso"),
      @ApiResponse(responseCode = "409", description = "Veiculo já cadastrado")
  })
  public ResponseEntity<?> cadastrarVeiculo(
      @RequestBody @Valid Veiculo veiculo,
      @PathVariable long empresaId,
      @RequestHeader("Authorization") String token) {
    validaVeiculoServico.validar(veiculo, token);
    veiculoRepositorio.save(veiculo);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
  @GetMapping("/{empresaId}/veiculos")
  @Operation(summary = "Obter todos os veiculos", description = "Retorna uma lista de todos os veiculos cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Veiculos encontrados", content = @Content(schema = @Schema(implementation = List.class))),
      @ApiResponse(responseCode = "404", description = "Nenhum veiculo cadastrado")
  })
  public ResponseEntity<List<Veiculo>> obterVeiculos(
      @PathVariable long empresaId,
      @RequestHeader("Authorization") String token) {
    List<Veiculo> veiculos = veiculoRepositorio.findAllByEmpresaId(empresaId);
    if (veiculos.isEmpty()) {
      throw new NaoEncontradoExcecao("Nenhum veiculo cadastrado");
    } else {
      adicionaLinkVeiculoServico.adicionarLink(veiculos, empresaId);
      for (Veiculo veiculo : veiculos) {
        var proprietario = usuariosApi.obterUsuarioPorId("Bearer " + token, veiculo.getProprietarioId());
        veiculo.setProprietario(proprietario);
      }
      ResponseEntity<List<Veiculo>> resposta = new ResponseEntity<>(veiculos, HttpStatus.OK);
      return resposta;
    }
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
  @GetMapping("/{empresaId}/veiculo/{id}")
  @Operation(summary = "Obter veiculo", description = "Retorna um veiculo específico com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Veiculo encontrado", content = @Content(schema = @Schema(implementation = Veiculo.class))),
      @ApiResponse(responseCode = "404", description = "Veiculo não encontrado")
  })
  public ResponseEntity<Veiculo> obterVeiculo(
      @PathVariable long id,
      @PathVariable long empresaId,
      @RequestHeader("Authorization") String token) {
    Optional<Veiculo> veiculo = veiculoRepositorio.findByIdAndEmpresaId(id, empresaId);
    if (veiculo.isEmpty()) {
      throw new NaoEncontradoExcecao("Veiculo não encontrado");
    }
    var proprietario = usuariosApi.obterUsuarioPorId("Bearer " + token, veiculo.get().getProprietarioId());
    veiculo.get().setProprietario(proprietario);
    adicionaLinkVeiculoServico.adicionarLink(veiculo.get(), empresaId);
    return ResponseEntity.status(HttpStatus.OK).body(veiculo.get());
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @PutMapping("/{empresaId}/veiculo/atualizar")
  @Operation(summary = "Atualizar veiculo", description = "Atualiza as informações de um veiculo existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Veiculo atualizado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Veiculo não encontrado"),
  })
  public ResponseEntity<?> atualizarVeiculo(
      @RequestBody Veiculo veiculoAtualizado,
      @PathVariable long empresaId,
      @RequestHeader("Authorization") String token) {
    Optional<Veiculo> veiculo = veiculoRepositorio.findById(veiculoAtualizado.getId());
    if (veiculo.isEmpty()) {
      throw new NaoEncontradoExcecao("Veiculo não encontrado");
    }
    validaVeiculoServico.validar(veiculoAtualizado, token);
    atualizaVeiculoServico.atualizar(veiculo.get(), veiculoAtualizado);
    veiculoRepositorio.save(veiculo.get());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
  @DeleteMapping("/{empresaId}/veiculo/excluir")
  @Operation(summary = "Excluir veiculo", description = "Exclui um veiculo existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Veiculo excluído com sucesso"),
      @ApiResponse(responseCode = "404", description = "Veiculo não encontrado")
  })
  public ResponseEntity<?> excluirVeiculo(
      @RequestBody Veiculo exclusao,
      @PathVariable long empresaId) {
    Optional<Veiculo> veiculo = veiculoRepositorio.findById(exclusao.getId());
    if (veiculo.isEmpty()) {
      throw new NaoEncontradoExcecao("Veiculo não encontrado");
    }
    veiculoRepositorio.delete(veiculo.get());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}