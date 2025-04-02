package br.com.autobots.automanager.servicos;

import java.util.Date;

import java.time.ZoneId;

public class VerificaDataServico {
  public boolean verificar(Date dado) {
    if (dado == null) {
      return false;
    }

    try {
      dado.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
