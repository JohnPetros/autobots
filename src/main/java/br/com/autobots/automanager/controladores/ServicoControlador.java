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

import br.com.autobots.automanager.entidades.Servico;
import br.com.autobots.automanager.repositorios.ServicoRepositorio;
import br.com.autobots.automanager.servicos.AdicionaLinkServicoServico;
import br.com.autobots.automanager.servicos.AtualizaServicoServico;

@RestController
@RequestMapping
public class ServicoControlador {
  @Autowired
  private ServicoRepositorio repositorio;

  @Autowired
  private AdicionaLinkServicoServico adicionaLinkServicoServico;

  @Autowired
  private AtualizaServicoServico atualizaServicoServico;

  @PostMapping("/servico/cadastrar")
  public ResponseEntity<?> cadastrarServico(@RequestBody Servico servico) {
    HttpStatus status = HttpStatus.CONFLICT;
    if (servico.getId() == null) {
      repositorio.save(servico);
      status = HttpStatus.CREATED;
    }
    return new ResponseEntity<>(status);
  }

  @GetMapping("/servicos")
  public ResponseEntity<List<Servico>> obterServicos() {
    List<Servico> servicos = repositorio.findAll();
    if (servicos.isEmpty()) {
      ResponseEntity<List<Servico>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkServicoServico.adicionarLink(servicos);
      ResponseEntity<List<Servico>> resposta = new ResponseEntity<>(servicos, HttpStatus.OK);
      return resposta;
    }
  }

  @GetMapping("/servico/{id}")
  public ResponseEntity<Servico> obterServico(@PathVariable long id) {
    Optional<Servico> cliente = repositorio.findById(id);
    if (cliente.isEmpty()) {
      ResponseEntity<Servico> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkServicoServico.adicionarLink(cliente.get());
      return ResponseEntity.status(HttpStatus.OK).body(cliente.get());
    }
  }

  @PutMapping("/servico/atualizar")
  public void atualizarServico(@RequestBody Servico servicoAtualizado) {
    var servico = repositorio.findById(servicoAtualizado.getId());
    atualizaServicoServico.atualizar(servico.get(), servicoAtualizado);
    repositorio.save(servico.get());
  }

  @DeleteMapping("/servico/excluir")
  public void excluirServico(@RequestBody Servico exclusao) {
    var servico = repositorio.findById(exclusao.getId());
    repositorio.delete(servico.get());
  }
}
