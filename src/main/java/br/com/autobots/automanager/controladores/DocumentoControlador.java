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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.autobots.automanager.entidades.Documento;
import br.com.autobots.automanager.repositorios.DocumentoRepositorio;
import br.com.autobots.automanager.servicos.AdicionaLinkDocumentoServico;
import br.com.autobots.automanager.servicos.AtualizaDocumentoServico;

@RestController
@RequestMapping("documentos")
public class DocumentoControlador {
  @Autowired
  private DocumentoRepositorio repositorio;

  @Autowired
  private AdicionaLinkDocumentoServico adicionaLinkDocumentoServico;

  @Autowired
  private AtualizaDocumentoServico atualizaDocumentoServico;

  @PostMapping("/cadastro")
  public void cadastrarDocumento(@RequestBody Documento documento) {
    repositorio.save(documento);
  }

  @GetMapping
  public ResponseEntity<List<Documento>> obterDocumentos() {
    List<Documento> documentos = repositorio.findAll();
    if (documentos.isEmpty()) {
      ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkDocumentoServico.adicionarLink(documentos);
      ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(documentos, HttpStatus.FOUND);
      return resposta;
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Documento> obterDocumento(@PathVariable long id) {
    Optional<Documento> cliente = repositorio.findById(id);
    if (cliente.isEmpty()) {
      ResponseEntity<Documento> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkDocumentoServico.adicionarLink(cliente.get());
      return ResponseEntity.status(HttpStatus.OK).body(cliente.get());
    }
  }

  @PutMapping("/atualizar")
  public void atualizarDocumento(@RequestBody Documento documentoAtualizado) {
    var documento = repositorio.findById(documentoAtualizado.getId());
    atualizaDocumentoServico.atualizar(documento.get(), documentoAtualizado);
    repositorio.save(documento.get());
  }

  @DeleteMapping("/excluir")
  public void excluirDocumento(@RequestBody Documento exclusao) {
    var documento = repositorio.findById(exclusao.getId());
    repositorio.delete(documento.get());
  }
}
