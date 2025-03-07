package br.com.autobots.automanager.controladores;

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

import br.com.autobots.automanager.entidades.Documento;
import br.com.autobots.automanager.repositorios.DocumentoRepositorio;
import br.com.autobots.automanager.servicos.AtualizaDocumentoServico;

@RestController
@RequestMapping("documento")
public class DocumentoControlador {
  @Autowired
  private DocumentoRepositorio documentoRepositorio;

  @Autowired
  private AtualizaDocumentoServico atualizaDocumentoServico;

  @PostMapping("/cadastro")
  public void cadastrarDocumento(@RequestBody Documento documento) {
    documentoRepositorio.save(documento);
  }

  @GetMapping("/documentos")
  public List<Documento> obterDocumentos() {
    List<Documento> documentos = documentoRepositorio.findAll();
    return documentos;
  }

  @GetMapping("/documento/{id}")
  public Documento obterDocumento(@PathVariable long id) {
    System.out.println(id);
    var documento = documentoRepositorio.findById(id);
    return documento.get();
  }

  @PutMapping("/atualizar")
  public void atualizarDocumento(@RequestBody Documento documentoAtualizado) {
    var documento = documentoRepositorio.findById(documentoAtualizado.getId());
    atualizaDocumentoServico.atualizar(documento.get(), documentoAtualizado);
    documentoRepositorio.save(documento.get());
  }

  @DeleteMapping("/excluir")
  public void excluirDocumento(@RequestBody Documento exclusao) {
    var documento = documentoRepositorio.findById(exclusao.getId());
    documentoRepositorio.delete(documento.get());
  }
}
