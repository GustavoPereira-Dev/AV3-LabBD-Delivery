package br.edu.fateczl.AvaliacaoDeliveryAV3.prato;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Set;

public record AtualizacaoPrato(
        String id,
        
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        
        @NotNull(message = "Tipo é obrigatório")
        Integer tipoId,
        
        @NotNull(message = "Valor é obrigatório")
        @Positive(message = "Valor deve ser positivo")
        Double valor

) {
}