package br.com.autobots.automanager.servicos;

import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Servico;

@Service
public class AtualizaServicoServico {

  public void atualizar(Servico servico, Servico servicoAtualizado) {
    if (servicoAtualizado.getNome() != null) {
      servicoAtualizado.setNome(servicoAtualizado.getNome());
    }
    if (servicoAtualizado.getValor() > 0) {
      servico.setValor(servicoAtualizado.getValor());
    }
    if (servicoAtualizado.getDescricao() != null) {
      servico.setDescricao(servicoAtualizado.getDescricao());
    }
  }
}
