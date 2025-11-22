package br.edu.fateczl.AvaliacaoDeliveryAV3.prato_ingrediente;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.fateczl.AvaliacaoDeliveryAV3.ingrediente.Ingrediente;
import br.edu.fateczl.AvaliacaoDeliveryAV3.ingrediente.IngredienteService;
import br.edu.fateczl.AvaliacaoDeliveryAV3.porcao.Porcao;
import br.edu.fateczl.AvaliacaoDeliveryAV3.porcao.PorcaoService;
import br.edu.fateczl.AvaliacaoDeliveryAV3.prato.Prato;
import br.edu.fateczl.AvaliacaoDeliveryAV3.prato.PratoService;

import java.util.List;

@Service
public class PratoIngredienteService {

    @Autowired private PratoIngredienteRepository repository;
    @Autowired private PratoService pratoService;
    @Autowired private IngredienteService ingredienteService;
    @Autowired private PorcaoService porcaoService;

    public List<PratoIngrediente> procurarTodos() {
        return repository.findAll();
    }

    public PratoIngrediente salvar(AtualizacaoPratoIngrediente dto) {
        // Busca as entidades completas
        Prato prato = pratoService.procurarPorId(dto.pratoId())
                .orElseThrow(() -> new EntityNotFoundException("Prato não encontrado"));
        
        Ingrediente ingrediente = ingredienteService.procurarPorId(dto.ingredienteId())
                .orElseThrow(() -> new EntityNotFoundException("Ingrediente não encontrado"));
        
        Porcao porcao = porcaoService.procurarPorId(dto.porcaoId())
                .orElseThrow(() -> new EntityNotFoundException("Porção não encontrada"));

        PratoIngrediente entidade = new PratoIngrediente(prato, ingrediente, porcao);
        
        return repository.save(entidade);
    }

    public void apagar(String pratoId, Integer ingredienteId, Integer porcaoId) {
        PratoIngredienteId id = new PratoIngredienteId(pratoId, ingredienteId, porcaoId);
        repository.deleteById(id);
    }
    
    public long contar() {
        return repository.count();
    }
}