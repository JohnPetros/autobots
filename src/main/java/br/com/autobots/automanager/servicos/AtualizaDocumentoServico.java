package br.com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Documento;

@Service
public class AtualizaDocumentoServico {
  @Autowired
  private VerificaStringNuloServico verificaStringServico;

  public void atualizar(Documento documento, Documento atualizacao) {
    if (atualizacao != null) {
      if (!verificaStringServico.verificar(atualizacao.getTipo())) {
        documento.setTipo(atualizacao.getTipo());
      }
      if (!verificaStringServico.verificar(atualizacao.getNumero())) {
        documento.setNumero(atualizacao.getNumero());
      }
    }
  }

  public void atualizar(List<Documento> documentos, List<Documento> atualizacoes) {
    for (Documento atualizacao : atualizacoes) {
      for (Documento documento : documentos) {
        if (atualizacao.getId() != null) {
          if (atualizacao.getId() == documento.getId()) {
            atualizar(documento, atualizacao);
          }
        }
      }
    }
  }
}
