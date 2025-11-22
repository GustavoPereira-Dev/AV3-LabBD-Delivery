package br.edu.fateczl.AvaliacaoDeliveryAV3.porcao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
public record AtualizacaoPorcao(
    Integer id,
    @NotBlank(message = "Tamanho é obrigatório")
    String tamanho,
    
    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    Double valor
) {}