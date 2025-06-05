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
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import br.com.autobots.automanager.entidades.Empresa;
import br.com.autobots.automanager.repositorios.EmpresaRepositorio;
import br.com.autobots.automanager.servicos.AdicionaLinkEmpresaServico;
import br.com.autobots.automanager.servicos.AtualizaEmpresaServico;

@RestController
@Tag(name = "Empresa", description = "CRUD de empresas")
public class EmpresaControlador {
  @Autowired
  private EmpresaRepositorio repositorio;

  @Autowired
  private AdicionaLinkEmpresaServico adicionaLinkEmpresaServico;

  @Autowired
  private AtualizaEmpresaServico atualizaEmpresaServico;

  @PostMapping("/empresa/cadastrar")
  @Operation(summary = "Cadastrar empresa", description = "Cadastra um novo empresa")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Empresa cadastrada com sucesso"),
      @ApiResponse(responseCode = "409", description = "Empresa já cadastrada")
  })
  public ResponseEntity<?> cadastrarEmpresa(@RequestBody Empresa empresa) {
    Optional<Empresa> empresaExistente = repositorio.findById(empresa.getId());
    if (empresaExistente.isPresent()) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    repositorio.save(empresa);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/empresas")
  @Operation(summary = "Obter todos os empresas", description = "Retorna uma lista de todos os empresas cadastradas")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Empresas encontradas", content = @Content(schema = @Schema(implementation = List.class))),
      @ApiResponse(responseCode = "404", description = "Nenhum empresa cadastrada")
  })
  public ResponseEntity<List<Empresa>> obterEmpresas() {
    List<Empresa> empresas = repositorio.findAll();
    if (empresas.isEmpty()) {
      ResponseEntity<List<Empresa>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkEmpresaServico.adicionarLink(empresas);
      ResponseEntity<List<Empresa>> resposta = new ResponseEntity<>(empresas, HttpStatus.OK);
      return resposta;
    }
  }

  @GetMapping("/empresa/{id}")
  @Operation(summary = "Obter empresa", description = "Retorna um empresa específico com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Empresa encontrada", content = @Content(schema = @Schema(implementation = Empresa.class))),
      @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
  })
  public ResponseEntity<Empresa> obterEmpresa(@PathVariable long id) {
    Optional<Empresa> cliente = repositorio.findById(id);
    if (cliente.isEmpty()) {
      ResponseEntity<Empresa> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkEmpresaServico.adicionarLink(cliente.get());
      return ResponseEntity.status(HttpStatus.OK).body(cliente.get());
    }
  }

  @PutMapping("/empresa/atualizar")
  @Operation(summary = "Atualizar empresa", description = "Atualiza as informações de um empresa existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Empresa atualizado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
  })
  public ResponseEntity<?> atualizarEmpresa(@RequestBody Empresa empresaAtualizado) {
    Optional<Empresa> empresa = repositorio.findById(empresaAtualizado.getId());
    if (empresa.isPresent()) {
      atualizaEmpresaServico.atualizar(empresa.get(), empresaAtualizado);
      repositorio.save(empresa.get());
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/empresa/excluir")
  @Operation(summary = "Excluir empresa", description = "Exclui um empresa existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Empresa excluído com sucesso"),
      @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
  })
  public ResponseEntity<?> excluirEmpresa(@RequestBody Empresa exclusao) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    Optional<Empresa> empresa = repositorio.findById(exclusao.getId());
    if (empresa.isPresent()) {
      repositorio.delete(empresa.get());
      status = HttpStatus.OK;
    }
    return new ResponseEntity<>(status);
  }
}
