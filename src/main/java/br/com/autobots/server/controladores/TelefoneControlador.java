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

import br.com.autobots.server.dtos.TelefoneDto;
import br.com.autobots.server.entidades.Telefone;
import br.com.autobots.server.repositorios.TelefoneRepositorio;
import br.com.autobots.server.servicos.AtualizaTelefoneServico;
import br.com.autobots.server.servicos.CadastraTelefoneServico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("telefone")
@Tag(name = "Telefone", description = "CRUD de telefones")
public class TelefoneControlador {
  @Autowired
  private TelefoneRepositorio telefoneRepositorio;

  @Autowired
  private AtualizaTelefoneServico atualizaTelefoneServico;

  @Autowired
  private CadastraTelefoneServico cadastraTelefoneServico;

  @PostMapping("/cadastro")
  @Operation(summary = "Cadastrar telefone", description = "Cadastra um novo telefone")
  public void cadastrarTelefone(@RequestBody TelefoneDto telefone) {
    var telefoneEntity = cadastraTelefoneServico.cadastrar(telefone);
    telefoneRepositorio.save(telefoneEntity);
  }

  @GetMapping("/telefones")
  @Operation(summary = "Obter todos os telefones", description = "Retorna uma lista de todos os telefones cadastrados")
  public List<Telefone> obtertelefones() {
    List<Telefone> Telefones = telefoneRepositorio.findAll();
    return Telefones;
  }

  @GetMapping("/telefone/{id}")
  @Operation(summary = "Obter telefone", description = "Retorna um telefone específico com base no ID fornecido")
  public Telefone obtertelefone(@PathVariable long id) {
    System.out.println(id);
    var Telefone = telefoneRepositorio.findById(id);
    return Telefone.get();
  }

  @PutMapping("/atualizar")
  @Operation(summary = "Atualizar telefone", description = "Atualiza as informações de um telefone existente")
  public void atualizartelefone(@RequestBody Telefone telefoneAtualizado) {
    var telefone = telefoneRepositorio.findById(telefoneAtualizado.getId());
    atualizaTelefoneServico.atualizar(telefone.get(), telefoneAtualizado);
    telefoneRepositorio.save(telefone.get());
  }

  @DeleteMapping("/excluir")
  @Operation(summary = "Excluir telefone", description = "Exclui um telefone existente")
  public void excluirtelfone(@RequestBody Telefone exclusao) {
    var telefone = telefoneRepositorio.findById(exclusao.getId());
    telefoneRepositorio.delete(telefone.get());
  }
}
