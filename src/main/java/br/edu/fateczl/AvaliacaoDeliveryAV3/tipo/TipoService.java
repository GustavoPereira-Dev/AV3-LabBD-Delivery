package br.edu.fateczl.AvaliacaoDeliveryAV3.tipo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.edu.fateczl.AvaliacaoDeliveryAV3.interfaces.IService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TipoService implements IService<Tipo, AtualizacaoTipo, Integer> {
    @Autowired private TipoRepository tipoRepository;

    @Override
    public Tipo salvarOuAtualizar(AtualizacaoTipo dto) {
        if (dto.id() != null) {
            Tipo existente = tipoRepository.findById(dto.id())
                    .orElseThrow(() -> new EntityNotFoundException("Tipo não encontrado com ID: " + dto.id()));
            existente.setId(dto.id());
            existente.setNome(dto.nome());
            return tipoRepository.save(existente);
        } else {
            Tipo novoTipo = new Tipo();
            novoTipo.setId(dto.id());
            novoTipo.setNome(dto.nome());
            return tipoRepository.save(novoTipo);
        }
    }
    
    @Override
    public List<Tipo> procurarTodos() {
        return tipoRepository.findAll(Sort.by("nome").ascending());
    }
    
    @Override
    public void apagarPorId(Integer id) {
        tipoRepository.deleteById(id);
    }
    
    @Override
    public Optional<Tipo> procurarPorId(Integer id) {
        return tipoRepository.findById(id);
    }
    
    @Override
    public long contar() {
        return tipoRepository.count();
    }
}