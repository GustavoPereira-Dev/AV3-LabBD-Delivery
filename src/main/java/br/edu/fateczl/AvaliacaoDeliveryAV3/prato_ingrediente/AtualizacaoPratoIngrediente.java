package br.edu.fateczl.AvaliacaoDeliveryAV3.prato_ingrediente;

import jakarta.validation.constraints.NotNull;

public record AtualizacaoPratoIngrediente(
    @NotNull(message = "O Prato é obrigatório")
    Integer pratoId,

    @NotNull(message = "O Ingrediente é obrigatório")
    Integer ingredienteId,

    @NotNull(message = "A Porção é obrigatória")
    Integer porcaoId
) {}