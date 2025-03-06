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

import br.com.autobots.server.entidades.Cliente;
import br.com.autobots.server.entidades.Documento;
import br.com.autobots.server.repositorios.ClienteRepositorio;
import br.com.autobots.server.repositorios.DocumentoRepositorio;
import br.com.autobots.server.servicos.AtualizaDocumentoServico;

@RestController
@RequestMapping("/documento")
public class DocumentoControlador {
  @Autowired
  private ClienteRepositorio clienteRepositorio;

  @Autowired
  private DocumentoRepositorio documentoRepositorio;

  @Autowired
  private AtualizaDocumentoServico atualizaDocumentoServico;

  @PostMapping("/cadastro")
  public void cadastrarDocumento(@RequestBody Documento documento) {
    Cliente cliente = clienteRepositorio
        .findById(documento.getCliente().getId())
        .orElse(null);
    documento.setCliente(cliente);
    documentoRepositorio.save(documento);
  }

  @GetMapping("/documentos")
  public List<Documento> obterDocumentos() {
    List<Documento> documentos = documentoRepositorio.findAll();
    return documentos;
  }

  @GetMapping("/documento/{id}")
  public Documento obterDocumento(@PathVariable long id) {
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
