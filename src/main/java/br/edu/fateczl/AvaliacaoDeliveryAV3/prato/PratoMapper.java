package br.edu.fateczl.AvaliacaoDeliveryAV3.prato;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import br.edu.fateczl.AvaliacaoDeliveryAV3.interfaces.IMapper;
import br.edu.fateczl.AvaliacaoDeliveryAV3.tipo.Tipo;

@Mapper(componentModel = "spring")
public interface PratoMapper extends IMapper<AtualizacaoPrato, Prato> {

    // Converte Entity para DTO
    @Mapping(target = "tipoId", source = "tipo.id")
    AtualizacaoPrato toAtualizacaoDto(Prato prato);

    // Converte DTO para Entity (Criação)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipo", source = "tipoId", qualifiedByName = "idToTipo")
    @Mapping(target = "pedidos", ignore = true)
    Prato toEntityFromAtualizacao(AtualizacaoPrato dto);

    // Atualiza Entity com DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipo", source = "tipoId", qualifiedByName = "idToTipo")
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

}