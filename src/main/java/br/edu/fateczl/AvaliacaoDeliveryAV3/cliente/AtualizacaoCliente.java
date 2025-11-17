package br.edu.fateczl.AvaliacaoDeliveryAV3.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AtualizacaoCliente(
        @NotBlank(message = "CPF é obrigatório")
        @Size(min = 11, max = 11, message = "CPF deve ter 11 caracteres")
        String cpf,

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        String telefone,
        String endereco,

        @NotNull(message = "Número é obrigatório")
        @Positive(message = "Número deve ser positivo")
        Integer numero,

        @Size(min = 8, max = 8, message = "CEP deve ter 8 caracteres")
        String cep,

        String pontoReferencia
) {
}