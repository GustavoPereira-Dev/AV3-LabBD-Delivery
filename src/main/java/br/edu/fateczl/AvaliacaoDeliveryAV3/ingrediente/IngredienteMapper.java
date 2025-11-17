package br.edu.fateczl.AvaliacaoDeliveryAV3.ingrediente;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IngredienteMapper {
    AtualizacaoIngrediente toAtualizacaoDto(Ingrediente ingrediente);
    @Mapping(target = "pratos", ignore = true)
    Ingrediente toEntityFromAtualizacao(AtualizacaoIngrediente dto);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pratos", ignore = true)
    void updateEntityFromDto(AtualizacaoIngrediente dto, @MappingTarget Ingrediente ingrediente);
}