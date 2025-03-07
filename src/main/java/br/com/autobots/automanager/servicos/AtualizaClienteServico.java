package br.com.autobots.automanager.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Cliente;

@Service
public class AtualizaClienteServico {
  @Autowired
  private VerificaStringNuloServico verificaStringServico;

  @Autowired
  private AtualizaEnderecoServico atualizaEnderoServico;

  @Autowired
  private AtualizaDocumentoServico atualizaDocumentoServico;

  @Autowired
  private AtualizaTelefoneServico atualizaTelefoneServico;

  public void atualizar(Cliente cliente, Cliente clienteAtualizado) {
    atualizarDados(cliente, clienteAtualizado);
    atualizaEnderoServico.atualizar(cliente.getEndereco(), clienteAtualizado.getEndereco());
    atualizaDocumentoServico.atualizar(cliente.getDocumentos(), clienteAtualizado.getDocumentos());
    atualizaTelefoneServico.atualizar(cliente.getTelefones(), clienteAtualizado.getTelefones());
  }

  private void atualizarDados(Cliente cliente, Cliente clienteAtualizado) {
    if (!verificaStringServico.verificar(clienteAtualizado.getNome())) {
      cliente.setNome(clienteAtualizado.getNome());
    }
    if (!verificaStringServico.verificar(clienteAtualizado.getNomeSocial())) {
      cliente.setNomeSocial(clienteAtualizado.getNomeSocial());
    }
    if (!(clienteAtualizado.getDataCadastro() == null)) {
      cliente.setDataCadastro(clienteAtualizado.getDataCadastro());
    }
    if (!(clienteAtualizado.getDataNascimento() == null)) {
      cliente.setDataNascimento(clienteAtualizado.getDataNascimento());
    }
  }

}
