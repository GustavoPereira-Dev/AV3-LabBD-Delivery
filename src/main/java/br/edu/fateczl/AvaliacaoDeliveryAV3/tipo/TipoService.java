package br.edu.fateczl.AvaliacaoDeliveryAV3.tipo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TipoService {
    @Autowired private TipoRepository tipoRepository;
    @Autowired private TipoMapper tipoMapper;

    public Tipo salvarOuAtualizar(AtualizacaoTipo dto) {
        if (dto.id() != null) {
            // Atualizando
            Tipo existente = tipoRepository.findById(dto.id())
                    .orElseThrow(() -> new EntityNotFoundException("Tipo não encontrado com ID: " + dto.id()));
            tipoMapper.updateEntityFromDto(dto, existente);
            return tipoRepository.save(existente);
        } else {
            // Criando
            Tipo novoTipo = tipoMapper.toEntityFromAtualizacao(dto);
            return tipoRepository.save(novoTipo);
        }
    }
    
    public List<Tipo> procurarTodos() {
        return tipoRepository.findAll(Sort.by("nome").ascending());
    }
    
    public void apagarPorId(Integer id) {
        tipoRepository.deleteById(id);
    }
    
    public Optional<Tipo> procurarPorId(Integer id) {
        return tipoRepository.findById(id);
    }
}