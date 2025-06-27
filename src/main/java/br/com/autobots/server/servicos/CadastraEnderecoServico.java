package br.com.autobots.server.servicos;

import org.springframework.stereotype.Service;

import br.com.autobots.server.dtos.EnderecoDto;
import br.com.autobots.server.entidades.Endereco;

@Service
public class CadastraEnderecoServico {
  public Endereco cadastrar(EnderecoDto endereco) {
    var enderecoEntity = new Endereco();
    enderecoEntity.setEstado(endereco.getEstado());
    enderecoEntity.setCidade(endereco.getCidade());
    enderecoEntity.setBairro(endereco.getBairro());
    enderecoEntity.setRua(endereco.getRua());
    enderecoEntity.setNumero(endereco.getNumero());
    enderecoEntity.setCodigoPostal(endereco.getCodigoPostal());
    enderecoEntity.setInformacoesAdicionais(endereco.getInformacoesAdicionais());
    enderecoEntity.setClienteId(endereco.getClienteId());
    return enderecoEntity;
  }
}
