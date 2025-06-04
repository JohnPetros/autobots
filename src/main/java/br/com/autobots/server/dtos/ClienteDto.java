package br.com.autobots.server.dtos;

import java.time.LocalDate;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ClienteDto {
  @Schema(description = "Nome do cliente", example = "João da Silva")
  private String nome;
  @Schema(description = "Nome social do cliente", example = "João")
  private String nomeSocial;
  @Schema(description = "Data de nascimento do cliente", example = "1990-01-01")
  private LocalDate dataNascimento;
  @Schema(description = "Data de cadastro do cliente", example = "2021-01-01")
  private LocalDate dataCadastro;
  @Schema(description = "Documentos do cliente")
  private List<DocumentoDto> documentos;
  @Schema(description = "Endereço do cliente")
  private EnderecoDto endereco;
  @Schema(description = "Telefones do cliente")
  private List<TelefoneDto> telefones;
}
