package br.edu.fateczl.AvaliacaoDeliveryAV3.prato;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import br.edu.fateczl.AvaliacaoDeliveryAV3.ingrediente.Ingrediente;
import br.edu.fateczl.AvaliacaoDeliveryAV3.tipo.Tipo;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PratoMapper {

    // Converte Entity para DTO
    @Mapping(target = "tipoId", source = "tipo.id")
    @Mapping(target = "ingredienteIds", source = "ingredientes", qualifiedByName = "ingredientesToIds")
    AtualizacaoPrato toAtualizacaoDto(Prato prato);

    // Converte DTO para Entity (Criação)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipo", source = "tipoId", qualifiedByName = "idToTipo")
    @Mapping(target = "ingredientes", ignore = true) // Será tratado no Service
    @Mapping(target = "pedidos", ignore = true)
    Prato toEntityFromAtualizacao(AtualizacaoPrato dto);

    // Atualiza Entity com DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipo", source = "tipoId", qualifiedByName = "idToTipo")
    @Mapping(target = "ingredientes", ignore = true) // Será tratado no Service
    @Mapping(target = "pedidos", ignore = true)
    void updateEntityFromDto(AtualizacaoPrato dto, @MappingTarget Prato prato);

    // --- Métodos Qualificadores ---

    @Named("idToTipo")
    default Tipo idToTipo(Integer tipoId) {
        if (tipoId == null) return null;
        Tipo tipo = new Tipo();
        tipo.setId(tipoId);
        return tipo;
    }

    @Named("ingredientesToIds")
    default Set<Integer> ingredientesToIds(Set<Ingrediente> ingredientes) {
        if (ingredientes == null) return null;
        return ingredientes.stream().map(Ingrediente::getId).collect(Collectors.toSet());
    }
}