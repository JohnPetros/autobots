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
import br.com.autobots.automanager.entidades.Documento;
import br.com.autobots.automanager.excecoes.NaoEncontradoExcecao;
import br.com.autobots.automanager.repositorios.DocumentoRepositorio;
import br.com.autobots.automanager.servicos.AdicionaLinkDocumentoServico;
import br.com.autobots.automanager.servicos.AtualizaDocumentoServico;
import br.com.autobots.automanager.servicos.ValidaDocumentoServico;

@RestController
@Tag(name = "Documento", description = "CRUD de documentos")
public class DocumentoControlador {
  @Autowired
  private DocumentoRepositorio repositorio;

  @Autowired
  private AdicionaLinkDocumentoServico adicionaLinkDocumentoServico;

  @Autowired
  private AtualizaDocumentoServico atualizaDocumentoServico;

  @Autowired
  private ValidaDocumentoServico validaDocumentoServico;

  @PostMapping("/documento/cadastrar")
  @Operation(summary = "Cadastrar documento", description = "Cadastra um novo documento")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Documento cadastrado com sucesso"),
      @ApiResponse(responseCode = "409", description = "Documento já cadastrado")
  })
  public ResponseEntity<?> cadastrarDocumento(@RequestBody @Valid Documento documento) {
    validaDocumentoServico.validar(documento);
    repositorio.save(documento);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/documentos")
  @Operation(summary = "Obter todos os documentos", description = "Retorna uma lista de todos os documentos cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Documentos encontrados", content = @Content(schema = @Schema(implementation = List.class))),
      @ApiResponse(responseCode = "404", description = "Nenhum documento cadastrado")
  })
  public ResponseEntity<List<Documento>> obterDocumentos() {
    List<Documento> documentos = repositorio.findAll();
    if (documentos.isEmpty()) {
      throw new NaoEncontradoExcecao("Nenhum documento cadastrado");
    } else {
      adicionaLinkDocumentoServico.adicionarLink(documentos, null);
      ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(documentos, HttpStatus.OK);
      return resposta;
    }
  }

  @GetMapping("/documento/{id}")
  @Operation(summary = "Obter documento", description = "Retorna um documento específico com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Documento encontrado", content = @Content(schema = @Schema(implementation = Documento.class))),
      @ApiResponse(responseCode = "404", description = "Documento não encontrado")
  })
  public ResponseEntity<Documento> obterDocumento(@PathVariable long id) {
    Optional<Documento> cliente = repositorio.findById(id);
    if (cliente.isEmpty()) {
      ResponseEntity<Documento> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkDocumentoServico.adicionarLink(cliente.get(), null);
      return ResponseEntity.status(HttpStatus.OK).body(cliente.get());
    }
  }

  @PutMapping("/documento/atualizar")
  @Operation(summary = "Atualizar documento", description = "Atualiza as informações de um documento existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Documento atualizado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Documento não encontrado")
  })
  public ResponseEntity<?> atualizarDocumento(@RequestBody Documento documentoAtualizado) {
    Optional<Documento> documento = repositorio.findById(documentoAtualizado.getId());
    if (documento.isEmpty()) {
      throw new NaoEncontradoExcecao("Documento não encontrado");
    }
    validaDocumentoServico.validar(documentoAtualizado);
    atualizaDocumentoServico.atualizar(documento.get(), documentoAtualizado);
    repositorio.save(documento.get());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/documento/excluir")
  @Operation(summary = "Excluir documento", description = "Exclui um documento existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Documento excluído com sucesso"),
      @ApiResponse(responseCode = "404", description = "Documento não encontrado")
  })
  public ResponseEntity<?> excluirDocumento(@RequestBody Documento exclusao) {
    Optional<Documento> documento = repositorio.findById(exclusao.getId());
    if (documento.isEmpty()) {
      throw new NaoEncontradoExcecao("Documento não encontrado");
    }
    repositorio.delete(documento.get());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}