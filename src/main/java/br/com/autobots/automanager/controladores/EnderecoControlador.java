package br.com.autobots.automanager.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.autobots.automanager.entidades.Endereco;
import br.com.autobots.automanager.repositorios.EnderecoRepositorio;
import br.com.autobots.automanager.servicos.AdicionaLinkEnderecoServico;
import br.com.autobots.automanager.servicos.AtualizaEnderecoServico;

@RestController
@RequestMapping
public class EnderecoControlador {
  @Autowired
  private EnderecoRepositorio repositorio;

  @Autowired
  private AdicionaLinkEnderecoServico adicionaLinkEnderecoServico;

  @Autowired
  private AtualizaEnderecoServico atualizaEnderecoServico;

  @PostMapping("/endereco/cadastrar")
  public ResponseEntity<?> cadastrarEndereco(@RequestBody Endereco endereco) {
    HttpStatus status = HttpStatus.CONFLICT;
    if (endereco.getId() == null) {
      repositorio.save(endereco);
      status = HttpStatus.CREATED;
    }
    return new ResponseEntity<>(status);
  }

  @GetMapping("/enderecos")
  public ResponseEntity<List<Endereco>> obterEnderecos() {
    List<Endereco> enderecos = repositorio.findAll();
    if (enderecos.isEmpty()) {
      ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkEnderecoServico.adicionarLink(enderecos);
      ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(enderecos, HttpStatus.OK);
      return resposta;
    }
  }

  @GetMapping("/endereco/{id}")
  public ResponseEntity<Endereco> obterEndereco(@PathVariable long id) {
    Optional<Endereco> cliente = repositorio.findById(id);
    if (cliente.isEmpty()) {
      ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkEnderecoServico.adicionarLink(cliente.get());
      return ResponseEntity.status(HttpStatus.OK).body(cliente.get());
    }
  }

  @PutMapping("/endereco/atualizar")
  public void atualizarEndereco(@RequestBody Endereco enderecoAtualizado) {
    var endereco = repositorio.findById(enderecoAtualizado.getId());
    atualizaEnderecoServico.atualizar(endereco.get(), enderecoAtualizado);
    repositorio.save(endereco.get());
  }

  @DeleteMapping("/endereco/excluir")
  public void excluirEndereco(@RequestBody Endereco exclusao) {
    var endereco = repositorio.findById(exclusao.getId());
    repositorio.delete(endereco.get());
  }
}
