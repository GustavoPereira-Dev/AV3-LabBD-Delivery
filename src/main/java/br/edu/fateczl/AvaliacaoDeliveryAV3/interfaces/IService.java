package br.edu.fateczl.AvaliacaoDeliveryAV3.interfaces;

import java.util.List;
import java.util.Optional;

public interface IService<T1, T2, T3> {

	public T1 salvarOuAtualizar(T2 dto);
	public List<T1> procurarTodos();
	public void apagarPorId(T3 id);
	public Optional<T1> procurarPorId(T3 id);
	public long contar();
	
}
