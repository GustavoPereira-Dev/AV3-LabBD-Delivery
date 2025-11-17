package br.edu.fateczl.AvaliacaoDeliveryAV3.porcao;

import jakarta.validation.constraints.NotBlank;
public record AtualizacaoPorcao(
    Integer id,
    @NotBlank(message = "Tamanho é obrigatório")
    String tamanho
) {}