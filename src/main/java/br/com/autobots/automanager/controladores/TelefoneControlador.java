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

import br.com.autobots.automanager.entidades.Telefone;
import br.com.autobots.automanager.repositorios.TelefoneRepositorio;
import br.com.autobots.automanager.servicos.AtualizaTelefoneServico;

@RestController
@RequestMapping("telefone")
public class TelefoneControlador {
  @Autowired
  private TelefoneRepositorio telefoneRepositorio;

  @Autowired
  private AtualizaTelefoneServico TtualizatelefoneServico;

  @PostMapping("/cadastro")
  public void Cadastrartelefone(@RequestBody Telefone Telefone) {
    telefoneRepositorio.save(Telefone);
  }

  @GetMapping("/telefones")
  public List<Telefone> Obtertelefones() {
    List<Telefone> Telefones = telefoneRepositorio.findAll();
    return Telefones;
  }

  @GetMapping("/telefone/{id}")
  public Telefone Obtertelefone(@PathVariable long id) {
    System.out.println(id);
    var Telefone = telefoneRepositorio.findById(id);
    return Telefone.get();
  }

  @PutMapping("/atualizar")
  public void Atualizartelefone(@RequestBody Telefone telefoneAtualizado) {
    var telefone = telefoneRepositorio.findById(telefoneAtualizado.getId());
    TtualizatelefoneServico.atualizar(telefone.get(), telefoneAtualizado);
    telefoneRepositorio.save(telefone.get());
  }

  @DeleteMapping("/excluir")
  public void Excluirtelefone(@RequestBody Telefone exclusao) {
    var telefone = telefoneRepositorio.findById(exclusao.getId());
    telefoneRepositorio.delete(telefone.get());
  }
}
