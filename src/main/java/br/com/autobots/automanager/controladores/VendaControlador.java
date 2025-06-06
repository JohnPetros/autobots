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

import br.com.autobots.automanager.entidades.Venda;
import br.com.autobots.automanager.repositorios.VendaRepositorio;
import br.com.autobots.automanager.servicos.AdicionaLinkVendaServico;
import br.com.autobots.automanager.servicos.AtualizaVendaServico;
import br.com.autobots.automanager.servicos.ValidaVendaServico;

@RestController
@Tag(name = "Venda", description = "CRUD de vendas")
public class VendaControlador {
  @Autowired
  private VendaRepositorio repositorio;

  @Autowired
  private AdicionaLinkVendaServico adicionaLinkVendaServico;

  @Autowired
  private AtualizaVendaServico atualizaVendaServico;

  @Autowired
  private ValidaVendaServico validaVendaServico;

  @PostMapping("/venda/cadastrar")
  @Operation(summary = "Cadastrar venda", description = "Cadastra um novo venda")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Venda cadastrado com sucesso"),
      @ApiResponse(responseCode = "422", description = "Venda não pode ser cadastrada"),
      @ApiResponse(responseCode = "409", description = "Venda já cadastrado")
  })
  public ResponseEntity<?> cadastrarVenda(@RequestBody Venda venda) {
    var vendaExistente = repositorio.findById(venda.getId());
    if (vendaExistente.isPresent()) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    if (!validaVendaServico.validar(venda)) {
      return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }
    repositorio.save(venda);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/vendas")
  @Operation(summary = "Obter todos os vendas", description = "Retorna uma lista de todos os vendas cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Vendas encontrados", content = @Content(schema = @Schema(implementation = List.class))),
      @ApiResponse(responseCode = "404", description = "Nenhum venda cadastrado")
  })
  public ResponseEntity<List<Venda>> obterVendas() {
    List<Venda> vendas = repositorio.findAll();
    if (vendas.isEmpty()) {
      ResponseEntity<List<Venda>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkVendaServico.adicionarLink(vendas);
      ResponseEntity<List<Venda>> resposta = new ResponseEntity<>(vendas, HttpStatus.OK);
      return resposta;
    }
  }

  @GetMapping("/venda/{id}")
  @Operation(summary = "Obter venda", description = "Retorna um venda específico com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Venda encontrado", content = @Content(schema = @Schema(implementation = Venda.class))),
      @ApiResponse(responseCode = "404", description = "Venda não encontrado")
  })
  public ResponseEntity<Venda> obterVenda(@PathVariable long id) {
    Optional<Venda> cliente = repositorio.findById(id);
    if (cliente.isEmpty()) {
      ResponseEntity<Venda> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkVendaServico.adicionarLink(cliente.get());
      return ResponseEntity.status(HttpStatus.OK).body(cliente.get());
    }
  }

  @PutMapping("/venda/atualizar")
  @Operation(summary = "Atualizar venda", description = "Atualiza as informações de um venda existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Venda atualizada com sucesso"),
      @ApiResponse(responseCode = "422", description = "Venda não pode ser atualizada"),
      @ApiResponse(responseCode = "404", description = "Venda não encontrada")
  })
  public ResponseEntity<?> atualizarVenda(@RequestBody Venda vendaAtualizado) {
    Optional<Venda> venda = repositorio.findById(vendaAtualizado.getId());
    if (venda.isPresent()) {
      if (!validaVendaServico.validar(vendaAtualizado)) {
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
      }
      atualizaVendaServico.atualizar(venda.get(), vendaAtualizado);
      repositorio.save(venda.get());
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/venda/excluir")
  @Operation(summary = "Excluir venda", description = "Exclui um venda existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Venda excluída com sucesso"),
      @ApiResponse(responseCode = "404", description = "Venda não encontrada")
  })
  public ResponseEntity<?> excluirVenda(@RequestBody Venda exclusao) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    Optional<Venda> venda = repositorio.findById(exclusao.getId());
    if (venda.isPresent()) {
      repositorio.delete(venda.get());
      status = HttpStatus.OK;
    }
    return new ResponseEntity<>(status);
  }
}