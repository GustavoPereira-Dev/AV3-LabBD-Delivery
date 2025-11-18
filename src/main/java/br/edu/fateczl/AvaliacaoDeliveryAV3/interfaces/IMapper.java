package br.edu.fateczl.AvaliacaoDeliveryAV3.interfaces;

import org.mapstruct.MappingTarget;

public interface IMapper<T1, T2> {

	T1 toAtualizacaoDto(T2 obj);
	T2 toEntityFromAtualizacao(T1 dto);
	void updateEntityFromDto(T1 dto, @MappingTarget T2 obj);
	
}
