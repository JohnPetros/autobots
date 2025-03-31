package br.com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AtualizaServicoServico {
  @Autowired
  private VerificaStringNuloServico verificaStringServico;

  public void atualizar(Servico servico, Servico atualizacao) {
    if (atualizacao != null) {
      if (!verificaStringServico.verificar(atualizacao.())) {
        endereco.setEstado(atualizacao.getEstado());
      }
    }
  }

}
