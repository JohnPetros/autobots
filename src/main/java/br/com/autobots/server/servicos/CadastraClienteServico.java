package br.com.autobots.server.servicos;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.autobots.server.dtos.ClienteDto;
import br.com.autobots.server.entidades.Cliente;
import br.com.autobots.server.entidades.Documento;
import br.com.autobots.server.entidades.Endereco;
import br.com.autobots.server.entidades.Telefone;

@Service
public class CadastraClienteServico {
  public Cliente cadastrar(ClienteDto cliente) {
    var clienteEntity = new Cliente();
    clienteEntity.setNome(cliente.getNome());
    clienteEntity.setNomeSocial(cliente.getNomeSocial());
    clienteEntity.setDataNascimento(cliente.getDataNascimento());
    clienteEntity.setDataCadastro(cliente.getDataCadastro());
    clienteEntity.setDocumentos(cliente.getDocumentos().stream().map(documento -> {
      var documentoEntity = new Documento();
      documentoEntity.setTipo(documento.getTipo());
      documentoEntity.setNumero(documento.getNumero());
      return documentoEntity;
    }).collect(Collectors.toList()));
    var enderecoEntity = new Endereco();
    enderecoEntity.setEstado(cliente.getEndereco().getEstado());
    enderecoEntity.setCidade(cliente.getEndereco().getCidade());
    enderecoEntity.setBairro(cliente.getEndereco().getBairro());
    enderecoEntity.setRua(cliente.getEndereco().getRua());
    enderecoEntity.setNumero(cliente.getEndereco().getNumero());
    enderecoEntity.setCodigoPostal(cliente.getEndereco().getCodigoPostal());
    clienteEntity.setEndereco(enderecoEntity);
    clienteEntity.setTelefones(cliente.getTelefones().stream().map(telefone -> {
      var telefoneEntity = new Telefone();
      telefoneEntity.setDdd(telefone.getDdd());
      telefoneEntity.setNumero(telefone.getNumero());
      return telefoneEntity;
    }).collect(Collectors.toList()));
    return clienteEntity;
  }
}
