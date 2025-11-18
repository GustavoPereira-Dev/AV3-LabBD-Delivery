package br.edu.fateczl.AvaliacaoDeliveryAV3.ingrediente;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import br.edu.fateczl.AvaliacaoDeliveryAV3.interfaces.IMapper;

@Mapper(componentModel = "spring")
public interface IngredienteMapper extends IMapper<AtualizacaoIngrediente, Ingrediente> {
    AtualizacaoIngrediente toAtualizacaoDto(Ingrediente ingrediente);
    @Mapping(target = "pratos", ignore = true)
    Ingrediente toEntityFromAtualizacao(AtualizacaoIngrediente dto);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pratos", ignore = true)
    void updateEntityFromDto(AtualizacaoIngrediente dto, @MappingTarget Ingrediente ingrediente);
}