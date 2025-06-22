package br.com.autobots.autenticacao.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.autenticacao.controladores.AutenticacaoControlador.Credencial;
import br.com.autobots.autenticacao.excecoes.AutenticacaoExcecao;
import br.com.autobots.autenticacao.provedores.AutenticacaoProvedor;
import br.com.autobots.autenticacao.provedores.JwtProvedor;
import br.com.autobots.autenticacao.entidades.Credencial;

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

  public String registrar(Credencial credencial) {
    autenticacaoProvedor.registrar(credencial);
    var jwt = jwtProvedor.gerarToken(credencial.getNomeUsuario());
    return jwt;
  }
}
