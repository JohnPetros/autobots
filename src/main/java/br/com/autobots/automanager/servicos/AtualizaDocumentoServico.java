package br.com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Documento;

@Service
public class AtualizaDocumentoServico {

  public void atualizar(Documento documento, Documento atualizacao) {
    if (atualizacao != null) {
      if (atualizacao.getTipo() != null) {
        documento.setTipo(atualizacao.getTipo());
      }
      if (atualizacao.getNumero() != null) {
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
