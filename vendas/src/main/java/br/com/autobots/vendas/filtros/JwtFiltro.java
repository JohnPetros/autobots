package br.com.autobots.vendas.filtros;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.autobots.vendas.entidades.Usuario;
import br.com.autobots.vendas.apis.UsuariosApi;
import br.com.autobots.vendas.provedores.JwtProvedor;
import br.com.autobots.vendas.seguranca.SegurancaUsuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFiltro extends OncePerRequestFilter {
  @Autowired
  JwtProvedor jwtProvedor;

  @Autowired
  UsuariosApi usuariosApi;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {

    var token = recoverToken(request);
    if (token != null) {
      var subject = jwtProvedor.validarToken(token);
      try {
        var usuario = getUsuario(token, subject);
        var segurancaUsuario = new SegurancaUsuario(usuario);
        var authentication = new UsernamePasswordAuthenticationToken(
            segurancaUsuario, null,
            segurancaUsuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (Exception e) {
        System.out.println("Erro ao autenticar usuário: " + e.getMessage());
        throw new BadCredentialsException("Credenciais inválidas");
      }
    }
    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request) {
    var authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return null;
    }
    return authHeader.replace("Bearer ", "");
  }

  private Usuario getUsuario(String token, String email) {
    var usuario = usuariosApi.obterUsuarioPorEmail("Bearer " + token, email);
    return usuario;
  }
}