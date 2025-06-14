package br.com.autobots.automanager.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.controladores.AutenticacaoControlador.Credencial;
import br.com.autobots.automanager.excecoes.AutenticacaoExcecao;
import br.com.autobots.automanager.provedores.AutenticacaoProvedor;
import br.com.autobots.automanager.provedores.JwtProvedor;

@Service
public class LoginServico {
  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  @Autowired
  private JwtProvedor jwtProvedor;

  public String login(Credencial credencial) {
    var credenciaisValidas = autenticacaoProvedor.validarCredenciais(credencial.getNomeUsuario(),
        credencial.getSenha());
    if (!credenciaisValidas) {
      throw new AutenticacaoExcecao("Credenciais inválidas");
    }
    var token = jwtProvedor.gerarToken(credencial.getNomeUsuario());
    return token;
  }
}
