package br.edu.fateczl.AvaliacaoDeliveryAV3.prato_ingrediente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PratoIngredienteRepository extends JpaRepository<PratoIngrediente, PratoIngredienteId> {
	
    @Query(value = "SELECT " +
            "    id_prato AS idPrato, " +
            "    id_ingrediente AS idIngrediente, " +
            "    id_porcao AS idPorcao, " +
            "    nome_prato AS nomePrato, " +
            "    nome_ingrediente AS nomeIngrediente, " +
            "    nome_porcao AS nomePorcao " +
            "FROM fn_pratos_ingredientes()", nativeQuery = true)
    		List<PratoIngredienteView> findAllViaFunction();
}