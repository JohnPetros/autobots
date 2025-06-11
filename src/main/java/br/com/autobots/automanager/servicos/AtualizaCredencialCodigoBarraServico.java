package br.com.autobots.automanager.servicos;

import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.CredencialCodigoBarra;

@Service
public class AtualizaCredencialCodigoBarraServico {
  public void atualizar(
      CredencialCodigoBarra credencialCodigoBarra,
      CredencialCodigoBarra credencialCodigoBarraAtualizada) {
    if (credencialCodigoBarraAtualizada == null) {
      return;
    }

    if (credencialCodigoBarraAtualizada.getCodigo() != null) {
      credencialCodigoBarra.setCodigo(credencialCodigoBarraAtualizada.getCodigo());
    }
    if (credencialCodigoBarraAtualizada.getCriacao() != null) {
      credencialCodigoBarra.setCriacao(credencialCodigoBarraAtualizada.getCriacao());
    }
  }
}
