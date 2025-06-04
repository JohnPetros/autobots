package br.com.autobots.server.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.autobots.server.dtos.DocumentoDto;
import br.com.autobots.server.entidades.Documento;
import br.com.autobots.server.repositorios.DocumentoRepositorio;
import br.com.autobots.server.servicos.AtualizaDocumentoServico;
import br.com.autobots.server.servicos.CadastraDocumentoServico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("documento")
@Tag(name = "Documento", description = "CRUD de documentos")
public class DocumentoControlador {
  @Autowired
  private DocumentoRepositorio documentoRepositorio;

  @Autowired
  private AtualizaDocumentoServico atualizaDocumentoServico;

  @Autowired
  private CadastraDocumentoServico cadastraDocumentoServico;

  @PostMapping("/cadastro")
  @Operation(summary = "Cadastrar documento", description = "Cadastra um novo documento")
  public void cadastrarDocumento(@RequestBody DocumentoDto documento) {
    var documentoEntity = cadastraDocumentoServico.cadastrar(documento);
    documentoRepositorio.save(documentoEntity);
  }

  @GetMapping("/documentos")
  @Operation(summary = "Obter todos os documentos", description = "Retorna uma lista de todos os documentos cadastrados")
  public List<Documento> obterDocumentos() {
    List<Documento> documentos = documentoRepositorio.findAll();
    return documentos;
  }

  @GetMapping("/documento/{id}")
  @Operation(summary = "Obter documento", description = "Retorna um documento específico com base no ID fornecido")
  public Documento obterDocumento(@PathVariable long id) {
    System.out.println(id);
    var documento = documentoRepositorio.findById(id);
    return documento.get();
  }

  @PutMapping("/atualizar")
  @Operation(summary = "Atualizar documento", description = "Atualiza as informações de um documento existente")
  public void atualizarDocumento(@RequestBody Documento documentoAtualizado) {
    var documento = documentoRepositorio.findById(documentoAtualizado.getId());
    atualizaDocumentoServico.atualizar(documento.get(), documentoAtualizado);
    documentoRepositorio.save(documento.get());
  }

  @DeleteMapping("/excluir")
  @Operation(summary = "Excluir documento", description = "Exclui um documento existente")
  public void excluirDocumento(@RequestBody Documento exclusao) {
    var documento = documentoRepositorio.findById(exclusao.getId());
    documentoRepositorio.delete(documento.get());
  }
}
