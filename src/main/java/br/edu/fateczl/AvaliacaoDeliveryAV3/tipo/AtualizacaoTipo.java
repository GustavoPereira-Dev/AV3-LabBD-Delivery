package br.edu.fateczl.AvaliacaoDeliveryAV3.tipo;

import jakarta.validation.constraints.NotBlank;
public record AtualizacaoTipo(
    Integer id,
    @NotBlank(message = "Nome é obrigatório")
    String nome
) {}