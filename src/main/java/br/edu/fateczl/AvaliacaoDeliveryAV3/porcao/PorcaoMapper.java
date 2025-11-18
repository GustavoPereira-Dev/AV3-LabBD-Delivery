package br.edu.fateczl.AvaliacaoDeliveryAV3.porcao;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import br.edu.fateczl.AvaliacaoDeliveryAV3.interfaces.IMapper;

@Mapper(componentModel = "spring")
public interface PorcaoMapper extends IMapper<AtualizacaoPorcao, Porcao> {
    AtualizacaoPorcao toAtualizacaoDto(Porcao porcao);
    @Mapping(target = "pedidos", ignore = true)
    Porcao toEntityFromAtualizacao(AtualizacaoPorcao dto);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedidos", ignore = true)
    void updateEntityFromDto(AtualizacaoPorcao dto, @MappingTarget Porcao porcao);
}