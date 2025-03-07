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

import br.com.autobots.automanager.entidades.Endereco;
import br.com.autobots.automanager.repositorios.EnderecoRepositorio;
import br.com.autobots.automanager.servicos.AtualizaEnderecoServico;

@RestController
@RequestMapping("endereco")
public class EnderecoControlador {
  @Autowired
  private EnderecoRepositorio enderecoRepositorio;

  @Autowired
  private AtualizaEnderecoServico TtualizaEnderecoServico;

  @PostMapping("/cadastro")
  public void CadastrarEndereco(@RequestBody Endereco endereco) {
    enderecoRepositorio.save(endereco);
  }

  @GetMapping("/enderecos")
  public List<Endereco> ObterEnderecos() {
    List<Endereco> Enderecos = enderecoRepositorio.findAll();
    return Enderecos;
  }

  @GetMapping("/endereco/{id}")
  public Endereco ObterEndereco(@PathVariable long id) {
    var endereco = enderecoRepositorio.findById(id);
    return endereco.get();
  }

  @PutMapping("/atualizar")
  public void AtualizarEndereco(@RequestBody Endereco EnderecoAtualizado) {
    var endereco = enderecoRepositorio.findById(EnderecoAtualizado.getId());
    TtualizaEnderecoServico.atualizar(endereco.get(), EnderecoAtualizado);
    enderecoRepositorio.save(endereco.get());
  }

  @DeleteMapping("/excluir")
  public void ExcluirEndereco(@RequestBody Endereco exclusao) {
    var endereco = enderecoRepositorio.findById(exclusao.getId());
    enderecoRepositorio.delete(endereco.get());
  }
}
