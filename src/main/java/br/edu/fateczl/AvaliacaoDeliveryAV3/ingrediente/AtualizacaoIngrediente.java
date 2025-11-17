package br.edu.fateczl.AvaliacaoDeliveryAV3.ingrediente;

import jakarta.validation.constraints.NotBlank;
public record AtualizacaoIngrediente(
    Integer id,
    @NotBlank(message = "Nome é obrigatório")
    String nome,
    String formatoApresentacao
) {}