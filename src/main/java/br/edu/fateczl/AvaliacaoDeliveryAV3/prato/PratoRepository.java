package br.edu.fateczl.AvaliacaoDeliveryAV3.prato;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import jakarta.transaction.Transactional;
public interface PratoRepository extends JpaRepository<Prato, String> {
	
	@Transactional
	@Procedure(name = "Prato.sp_gera_id_prato")
	public String sp_gera_id_prato();
	
}