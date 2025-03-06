package br.com.autobots.server.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.server.entidades.Endereco;

@Service
public class AtualizaEnderoServico {
  @Autowired
  private VerificaStringNuloServico verificaStringServico;

  public void atualizar(Endereco endereco, Endereco atualizacao) {
    if (atualizacao != null) {
      if (!verificaStringServico.verificar(atualizacao.getEstado())) {
        endereco.setEstado(atualizacao.getEstado());
      }
      if (!verificaStringServico.verificar(atualizacao.getCidade())) {
        endereco.setCidade(atualizacao.getCidade());
      }
      if (!verificaStringServico.verificar(atualizacao.getBairro())) {
        endereco.setBairro(atualizacao.getBairro());
      }
      if (!verificaStringServico.verificar(atualizacao.getRua())) {
        endereco.setRua(atualizacao.getRua());
      }
      if (!verificaStringServico.verificar(atualizacao.getNumero())) {
        endereco.setNumero(atualizacao.getNumero());
      }
      if (!verificaStringServico.verificar(atualizacao.getInformacoesAdicionais())) {
        endereco.setInformacoesAdicionais(atualizacao.getInformacoesAdicionais());
      }
    }
  }
}
