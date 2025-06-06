package br.com.autobots.automanager.servicos;

import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.CredencialCodigoBarra;

@Service
public class AtualizaCredencialCodigoBarraServico {
  public void atualizar(
      CredencialCodigoBarra credencialCodigoBarra,
      CredencialCodigoBarra credencialCodigoBarraAtualizada) {
    credencialCodigoBarra.setCodigo(credencialCodigoBarraAtualizada.getCodigo());
    credencialCodigoBarra.setCriacao(credencialCodigoBarraAtualizada.getCriacao());
    credencialCodigoBarra.setUltimoAcesso(credencialCodigoBarraAtualizada.getUltimoAcesso());
    credencialCodigoBarra.setInativo(credencialCodigoBarraAtualizada.isInativo());
  }
}
