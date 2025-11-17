package br.edu.fateczl.AvaliacaoDeliveryAV3.pedido;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AtualizacaoPedido(
        Integer id,
        
        @NotNull(message = "Valor é obrigatório")
        @Positive(message = "Valor deve ser positivo")
        Double valor,
        
        @NotBlank(message = "Cliente é obrigatório")
        String clienteCpf,
        
        @NotNull(message = "Prato é obrigatório")
        Integer pratoId,
        
        @NotNull(message = "Porção é obrigatória")
        Integer porcaoId
) {
}