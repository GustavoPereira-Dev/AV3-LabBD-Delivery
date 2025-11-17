package br.edu.fateczl.AvaliacaoDeliveryAV3.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ClienteRepository extends JpaRepository<Cliente, String> { // Chave primária é String (CPF)
}