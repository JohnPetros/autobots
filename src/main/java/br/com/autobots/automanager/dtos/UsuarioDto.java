package br.com.autobots.automanager.dtos;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

import br.com.autobots.automanager.entidades.Credencial;
import br.com.autobots.automanager.entidades.Documento;
import br.com.autobots.automanager.entidades.Email;
import br.com.autobots.automanager.entidades.Endereco;
import br.com.autobots.automanager.entidades.Mercadoria;
import br.com.autobots.automanager.entidades.Telefone;
import br.com.autobots.automanager.entidades.Veiculo;
import br.com.autobots.automanager.entidades.Venda;

@Data
public class UsuarioDto {
  private Long id;
  private String nome;
  private String nomeSocial;
  private LocalDate dataNascimento;
  private LocalDate dataCadastro;
  private List<Credencial> credenciais;
  private List<Documento> documentos;
  private List<Email> emails;
  private Endereco endereco;
  private List<Telefone> telefones;
  private List<Mercadoria> mercadorias;
  private List<Veiculo> veiculos;
  private List<Venda> vendas;
}
