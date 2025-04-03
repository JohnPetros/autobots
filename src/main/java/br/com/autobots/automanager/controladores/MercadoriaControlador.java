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

import br.com.autobots.automanager.entidades.Mercadoria;
import br.com.autobots.automanager.repositorios.MercadoriaRepositorio;
import br.com.autobots.automanager.servicos.AdicionaLinkMercadoriaServico;
import br.com.autobots.automanager.servicos.AtualizaMercadoriaServico;

@RestController
@RequestMapping
public class MercadoriaControlador {
  @Autowired
  private MercadoriaRepositorio repositorio;

  @Autowired
  private AdicionaLinkMercadoriaServico adicionaLinkMercadoriaServico;

  @Autowired
  private AtualizaMercadoriaServico atualizaMercadoriaServico;

  @PostMapping("/mercadoria/cadastrar")
  public ResponseEntity<?> cadastrarMercadoria(@RequestBody Mercadoria mercadoria) {
    HttpStatus status = HttpStatus.CONFLICT;
    if (mercadoria.getId() == null) {
      repositorio.save(mercadoria);
      status = HttpStatus.CREATED;
    }
    return new ResponseEntity<>(status);
  }

  @GetMapping("/mercadorias")
  public ResponseEntity<List<Mercadoria>> obterMercadorias() {
    List<Mercadoria> mercadorias = repositorio.findAll();
    if (mercadorias.isEmpty()) {
      ResponseEntity<List<Mercadoria>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkMercadoriaServico.adicionarLink(mercadorias);
      ResponseEntity<List<Mercadoria>> resposta = new ResponseEntity<>(mercadorias, HttpStatus.OK);
      return resposta;
    }
  }

  @GetMapping("/mercadoria/{id}")
  public ResponseEntity<Mercadoria> obterMercadoria(@PathVariable long id) {
    Optional<Mercadoria> cliente = repositorio.findById(id);
    if (cliente.isEmpty()) {
      ResponseEntity<Mercadoria> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkMercadoriaServico.adicionarLink(cliente.get());
      return ResponseEntity.status(HttpStatus.OK).body(cliente.get());
    }
  }

  @PutMapping("/mercadoria/atualizar")
  public void atualizarMercadoria(@RequestBody Mercadoria mercadoriaAtualizado) {
    var mercadoria = repositorio.findById(mercadoriaAtualizado.getId());
    atualizaMercadoriaServico.atualizar(mercadoria.get(), mercadoriaAtualizado);
    repositorio.save(mercadoria.get());
  }

  @DeleteMapping("/mercadoria/excluir")
  public void excluirMercadoria(@RequestBody Mercadoria exclusao) {
    var mercadoria = repositorio.findById(exclusao.getId());
    repositorio.delete(mercadoria.get());
  }
}
