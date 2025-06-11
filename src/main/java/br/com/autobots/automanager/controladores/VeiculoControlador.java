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

import br.com.autobots.automanager.entidades.Veiculo;
import br.com.autobots.automanager.repositorios.VeiculoRepositorio;
import br.com.autobots.automanager.servicos.AdicionaLinkVeiculoServico;
import br.com.autobots.automanager.servicos.AtualizaVeiculoServico;
import br.com.autobots.automanager.servicos.ValidaVeiculoServico;

@RestController
@Tag(name = "Veiculo", description = "CRUD de veiculos")
public class VeiculoControlador {
  @Autowired
  private VeiculoRepositorio repositorio;

  @Autowired
  private AdicionaLinkVeiculoServico adicionaLinkVeiculoServico;

  @Autowired
  private AtualizaVeiculoServico atualizaVeiculoServico;

  @Autowired
  private ValidaVeiculoServico validaVeiculoServico;

  @PostMapping("/veiculo/cadastrar")
  @Operation(summary = "Cadastrar veiculo", description = "Cadastra um novo veiculo")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Veiculo cadastrado com sucesso"),
      @ApiResponse(responseCode = "302", description = "Veiculo já cadastrado"),
      @ApiResponse(responseCode = "302", description = "Veiculo já cadastrado com a placa"),
      @ApiResponse(responseCode = "409", description = "Veiculo já cadastrado")
  })
  public ResponseEntity<?> cadastrarVeiculo(@RequestBody Veiculo veiculo) {
    validaVeiculoServico.validar(veiculo);
    repositorio.save(veiculo);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/veiculos")
  @Operation(summary = "Obter todos os veiculos", description = "Retorna uma lista de todos os veiculos cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Veiculos encontradas", content = @Content(schema = @Schema(implementation = List.class))),
      @ApiResponse(responseCode = "404", description = "Nenhum veiculo cadastrado")
  })
  public ResponseEntity<List<Veiculo>> obterVeiculos() {
    List<Veiculo> veiculos = repositorio.findAll();
    if (veiculos.isEmpty()) {
      ResponseEntity<List<Veiculo>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkVeiculoServico.adicionarLink(veiculos);
      ResponseEntity<List<Veiculo>> resposta = new ResponseEntity<>(veiculos, HttpStatus.OK);
      return resposta;
    }
  }

  @GetMapping("/veiculo/{id}")
  @Operation(summary = "Obter veiculo", description = "Retorna um veiculo específico com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Veiculo encontrada", content = @Content(schema = @Schema(implementation = Veiculo.class))),
      @ApiResponse(responseCode = "404", description = "Veiculo não encontrada")
  })
  public ResponseEntity<Veiculo> obterVeiculo(@PathVariable long id) {
    Optional<Veiculo> cliente = repositorio.findById(id);
    if (cliente.isEmpty()) {
      ResponseEntity<Veiculo> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkVeiculoServico.adicionarLink(cliente.get());
      return ResponseEntity.status(HttpStatus.OK).body(cliente.get());
    }
  }

  @PutMapping("/veiculo/atualizar")
  @Operation(summary = "Atualizar veiculo", description = "Atualiza as informações de um veiculo existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Veiculo atualizado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Veiculo não encontrada")
  })
  public ResponseEntity<?> atualizarVeiculo(@RequestBody Veiculo veiculoAtualizado) {
    Optional<Veiculo> veiculo = repositorio.findById(veiculoAtualizado.getId());
    if (veiculo.isPresent()) {
      atualizaVeiculoServico.atualizar(veiculo.get(), veiculoAtualizado);
      repositorio.save(veiculo.get());
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/veiculo/excluir")
  @Operation(summary = "Excluir veiculo", description = "Exclui um veiculo existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Veiculo excluído com sucesso"),
      @ApiResponse(responseCode = "404", description = "Veiculo não encontrada")
  })
  public ResponseEntity<?> excluirVeiculo(@RequestBody Veiculo exclusao) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    Optional<Veiculo> veiculo = repositorio.findById(exclusao.getId());
    if (veiculo.isPresent()) {
      repositorio.delete(veiculo.get());
      status = HttpStatus.OK;
    }
    return new ResponseEntity<>(status);
  }
}
