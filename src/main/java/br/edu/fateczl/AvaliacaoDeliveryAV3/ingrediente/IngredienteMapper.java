package br.edu.fateczl.AvaliacaoDeliveryAV3.ingrediente;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import br.edu.fateczl.AvaliacaoDeliveryAV3.interfaces.IMapper;

@Mapper(componentModel = "spring")
public interface IngredienteMapper extends IMapper<AtualizacaoIngrediente, Ingrediente> {
}