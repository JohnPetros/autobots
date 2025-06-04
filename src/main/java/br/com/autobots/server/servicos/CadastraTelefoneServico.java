package br.com.autobots.server.servicos;

import org.springframework.stereotype.Service;

import br.com.autobots.server.dtos.TelefoneDto;
import br.com.autobots.server.entidades.Telefone;

@Service
public class CadastraTelefoneServico {
  public Telefone cadastrar(TelefoneDto telefone) {
    var telefoneEntity = new Telefone();
    telefoneEntity.setDdd(telefone.getDdd());
    telefoneEntity.setNumero(telefone.getNumero());
    return telefoneEntity;
  }
}
