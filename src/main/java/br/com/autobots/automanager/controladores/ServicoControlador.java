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

@RestController
@RequestMapping("servicos")
public class ServicoControlador {
  @Autowired
  private ServicoRepositorio repositorio;

  @Autowired
  private AtualizaServicoServico atualizaServicoServico;

  @Autowired
  private AdicionaLinkServicoServico adicionaLinkServicoServico;

  @PostMapping
  public void cadastrarServico(@RequestBody Servico servico) {
    repositorio.save(servico);
  }

  @GetMapping
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

  @GetMapping("/{id}")
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

  @PutMapping
  public void atualizarServico(@RequestBody Servico ServicoAtualizado) {
    var servico = repositorio.findById(ServicoAtualizado.getId());
    atualizaServicoServico.atualizar(servico.get(), ServicoAtualizado);
    repositorio.save(servico.get());
  }

  @DeleteMapping
  public void excluirServico(@RequestBody Servico exclusao) {
    var servico = repositorio.findById(exclusao.getId());
    repositorio.delete(servico.get());
  }
}
