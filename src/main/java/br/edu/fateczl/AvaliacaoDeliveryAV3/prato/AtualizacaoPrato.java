package br.edu.fateczl.AvaliacaoDeliveryAV3.prato;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record AtualizacaoPrato(
        Integer id,
        
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        
        @NotNull(message = "Tipo é obrigatório")
        Integer tipoId,

        // IDs dos ingredientes selecionados
        Set<Integer> ingredienteIds
) {
}