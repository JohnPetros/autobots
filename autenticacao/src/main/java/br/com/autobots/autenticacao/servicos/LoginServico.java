package br.com.autobots.autenticacao.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.autobots.autenticacao.excecoes.AutenticacaoExcecao;
import br.com.autobots.autenticacao.provedores.JwtProvedor;
import br.com.autobots.autenticacao.entidades.Credencial;
import br.com.autobots.autenticacao.repositorios.CredencialRepositorio;

@Service
public class LoginServico {
  @Autowired
  private JwtProvedor jwtProvedor;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private CredencialRepositorio credencialRepositorio;

  public String login(Credencial credencial) {
    var credencialExistente = credencialRepositorio.findByEmail(credencial.getEmail());
    if (credencialExistente.isEmpty()) {
      throw new AutenticacaoExcecao("Credencial não encontrada");
    }
    boolean credenciaisValidas = passwordEncoder.matches(credencial.getSenha(), credencialExistente.get().getSenha());
    if (!credenciaisValidas) {
      throw new AutenticacaoExcecao("Credenciais inválidas");
    }

    var token = jwtProvedor.gerarToken(credencialExistente.get().getEmail());
    return token;
  }
}
