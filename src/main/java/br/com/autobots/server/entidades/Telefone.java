package br.com.autobots.server.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Entity(name = "telefones")
public class Telefone {
	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "ID do telefone", example = "1")
	private Long id;
	@Column
	@Schema(description = "DDD do telefone", example = "11")
	private String ddd;
	@Column
	@Schema(description = "Número do telefone", example = "999999999")
	private String numero;
}