package br.com.autobots.veiculos;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.autobots.veiculos.excecoes.AutenticacaoExcecao;
import br.com.autobots.veiculos.excecoes.ConflitoExcecao;
import br.com.autobots.veiculos.excecoes.NaoEncontradoExcecao;
import br.com.autobots.veiculos.excecoes.UsuarioNaoAutorizadoExcecao;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Data;

@ControllerAdvice
public class ExcecaoControlador {
  @Data
  @AllArgsConstructor
  private static class Mensagem {
    private String message;
  }

  @ExceptionHandler(NaoEncontradoExcecao.class)
  private ResponseEntity<Mensagem> excecaoNaoEncontrado(NaoEncontradoExcecao exception) {
    var message = new Mensagem(exception.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
  }

  @ExceptionHandler(ConflitoExcecao.class)
  private ResponseEntity<Mensagem> excecaoConflito(ConflitoExcecao exception) {
    var message = new Mensagem(exception.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  private ResponseEntity<Map<String, String>> excecaoArgumentoInvalido(MethodArgumentNotValidException exception) {
    var errors = new HashMap<String, String>();
    exception.getBindingResult().getFieldErrors()
        .forEach(error -> errors.put("campo: " + error.getField(), error.getDefaultMessage()));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  private ResponseEntity<Map<String, String>> excecaoArgumentoInvalido(ConstraintViolationException exception) {
    var errors = new HashMap<String, String>();
    exception.getConstraintViolations()
        .forEach(error -> errors.put("campo " + error.getPropertyPath(), error.getMessage()));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
  private ResponseEntity<Mensagem> excecaoArgumentoInvalido(
      org.springframework.http.converter.HttpMessageNotReadableException exception) {
    var message = new Mensagem(exception.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
  }

  @ExceptionHandler(AutenticacaoExcecao.class)
  private ResponseEntity<Mensagem> excecaoAutenticacao(AutenticacaoExcecao exception) {
    var message = new Mensagem(exception.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
  }

  @ExceptionHandler(UsuarioNaoAutorizadoExcecao.class)
  private ResponseEntity<Mensagem> excecaoUsuarioNaoAutorizado(UsuarioNaoAutorizadoExcecao exception) {
    var message = new Mensagem(exception.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
  }

  @ExceptionHandler(AccessDeniedException.class)
  private ResponseEntity<Mensagem> excecaoUsuarioNaoAutorizado(AccessDeniedException exception) {
    var message = new Mensagem(exception.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
  }
}