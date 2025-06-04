package br.com.autobots.server.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TelefoneDto {
  @Schema(description = "DDD do telefone", example = "11")
  private String ddd;
  @Schema(description = "Número do telefone", example = "999999999")
  private String numero;
}
