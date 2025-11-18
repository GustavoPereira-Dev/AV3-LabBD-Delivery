package br.edu.fateczl.AvaliacaoDeliveryAV3.tipo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import br.edu.fateczl.AvaliacaoDeliveryAV3.interfaces.IMapper;

@Mapper(componentModel = "spring")
public interface TipoMapper extends IMapper<AtualizacaoTipo, Tipo> {
    AtualizacaoTipo toAtualizacaoDto(Tipo tipo);
    @Mapping(target = "pratos", ignore = true)
    Tipo toEntityFromAtualizacao(AtualizacaoTipo dto);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pratos", ignore = true)
    void updateEntityFromDto(AtualizacaoTipo dto, @MappingTarget Tipo tipo);
}