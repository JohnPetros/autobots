package br.com.autobots.usuarios.servicos;

import org.springframework.stereotype.Service;

@Service
public class VerificaStringNuloServico {
  public boolean verificar(String dado) {
    boolean nulo = true;
    if (!(dado == null)) {
      if (!dado.isBlank()) {
        nulo = false;
      }
    }
    return nulo;
  }
}
