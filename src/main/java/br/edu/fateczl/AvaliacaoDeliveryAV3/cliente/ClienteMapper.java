package br.edu.fateczl.AvaliacaoDeliveryAV3.cliente;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    // Converte Entity para DTO
    AtualizacaoCliente toAtualizacaoDto(Cliente cliente);

    // Converte DTO para Entity (para criação NOVA - ignora ID/relações)
    @Mapping(target = "pedidos", ignore = true)
    Cliente toEntityFromAtualizacao(AtualizacaoCliente dto);

    // Atualiza Entity existente com dados do DTO
    @Mapping(target = "cpf", ignore = true) // CPF é a ID, não deve ser atualizado
    @Mapping(target = "pedidos", ignore = true)
    void updateEntityFromDto(AtualizacaoCliente dto, @MappingTarget Cliente cliente);
}