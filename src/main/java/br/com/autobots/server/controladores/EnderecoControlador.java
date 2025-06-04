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

import br.com.autobots.server.dtos.EnderecoDto;
import br.com.autobots.server.entidades.Endereco;
import br.com.autobots.server.repositorios.EnderecoRepositorio;
import br.com.autobots.server.servicos.AtualizaEnderecoServico;
import br.com.autobots.server.servicos.CadastraEnderecoServico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("endereco")
@Tag(name = "Endereço", description = "CRUD de endereços")
public class EnderecoControlador {
  @Autowired
  private EnderecoRepositorio enderecoRepositorio;

  @Autowired
  private AtualizaEnderecoServico TtualizaEnderecoServico;

  @Autowired
  private CadastraEnderecoServico cadastraEnderecoServico;

  @PostMapping("/cadastro")
  @Operation(summary = "Cadastrar endereço", description = "Cadastra um novo endereço")
  public void CadastrarEndereco(@RequestBody EnderecoDto endereco) {
    var enderecoEntity = cadastraEnderecoServico.cadastrar(endereco);
    enderecoRepositorio.save(enderecoEntity);
  }

  @GetMapping("/enderecos")
  @Operation(summary = "Obter todos os endereços", description = "Retorna uma lista de todos os endereços cadastrados")
  public List<Endereco> ObterEnderecos() {
    List<Endereco> Enderecos = enderecoRepositorio.findAll();
    return Enderecos;
  }

  @GetMapping("/endereco/{id}")
  @Operation(summary = "Obter endereço", description = "Retorna um endereço específico com base no ID fornecido")
  public Endereco ObterEndereco(@PathVariable long id) {
    var endereco = enderecoRepositorio.findById(id);
    return endereco.get();
  }

  @PutMapping("/atualizar")
  @Operation(summary = "Atualizar endereço", description = "Atualiza as informações de um endereço existente")
  public void AtualizarEndereco(@RequestBody Endereco EnderecoAtualizado) {
    var endereco = enderecoRepositorio.findById(EnderecoAtualizado.getId());
    TtualizaEnderecoServico.atualizar(endereco.get(), EnderecoAtualizado);
    enderecoRepositorio.save(endereco.get());
  }

  @DeleteMapping("/excluir")
  @Operation(summary = "Excluir endereço", description = "Exclui um endereço existente")
  public void ExcluirEndereco(@RequestBody Endereco exclusao) {
    var endereco = enderecoRepositorio.findById(exclusao.getId());
    enderecoRepositorio.delete(endereco.get());
  }
}
