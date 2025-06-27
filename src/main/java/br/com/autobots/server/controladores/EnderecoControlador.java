package br.com.autobots.server.controladores;

import java.util.List;
import java.util.Optional;

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
import br.com.autobots.server.entidades.Cliente;
import br.com.autobots.server.entidades.Endereco;
import br.com.autobots.server.repositorios.ClienteRepositorio;
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
  private AtualizaEnderecoServico atualizaEnderecoServico;

  @Autowired
  private CadastraEnderecoServico cadastraEnderecoServico;

  @Autowired
  private ClienteRepositorio clienteRepositorio;

  @PostMapping("/cadastro")
  @Operation(summary = "Cadastrar endereço", description = "Cadastra um novo endereço")
  public void cadastrarEndereco(@RequestBody EnderecoDto endereco) {
    var enderecoEntity = cadastraEnderecoServico.cadastrar(endereco);
    enderecoRepositorio.save(enderecoEntity);

    if (endereco.getClienteId() != null) {
      Optional<Cliente> cliente = clienteRepositorio.findById(endereco.getClienteId());
      if (cliente.isPresent()) {
        Cliente clienteEntity = cliente.get();
        clienteEntity.setEndereco(enderecoEntity);
        clienteRepositorio.save(clienteEntity);
      }
    }
  }

  @GetMapping("/enderecos")
  @Operation(summary = "Obter todos os endereços", description = "Retorna uma lista de todos os endereços cadastrados")
  public List<Endereco> obterEnderecos() {
    List<Endereco> enderecos = enderecoRepositorio.findAll();
    return enderecos;
  }

  @GetMapping("/endereco/{id}")
  @Operation(summary = "Obter endereço", description = "Retorna um endereço específico com base no ID fornecido")
  public Endereco obterEndereco(@PathVariable long id) {
    Optional<Endereco> endereco = enderecoRepositorio.findById(id);
    return endereco.get();
  }

  @PutMapping("/atualizar")
  @Operation(summary = "Atualizar endereço", description = "Atualiza as informações de um endereço existente")
  public void atualizarEndereco(@RequestBody Endereco enderecoAtualizado) {
    Optional<Endereco> endereco = enderecoRepositorio.findById(enderecoAtualizado.getId());
    atualizaEnderecoServico.atualizar(endereco.get(), enderecoAtualizado);
    enderecoRepositorio.save(endereco.get());
  }

  @DeleteMapping("/excluir")
  @Operation(summary = "Excluir endereço", description = "Exclui um endereço existente")
  public void excluirEndereco(@RequestBody Endereco exclusao) {
    Optional<Endereco> endereco = enderecoRepositorio.findById(exclusao.getId());
    if (endereco.isPresent()) {
      var cliente = clienteRepositorio.findByEndereco(endereco.get());
      if (cliente.isPresent()) {
        cliente.get().setEndereco(null);
        clienteRepositorio.save(cliente.get());
      }
      enderecoRepositorio.delete(endereco.get());
    }
  }
}
