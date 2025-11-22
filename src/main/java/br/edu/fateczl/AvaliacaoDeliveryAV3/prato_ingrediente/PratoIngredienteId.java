package br.edu.fateczl.AvaliacaoDeliveryAV3.prato_ingrediente;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PratoIngredienteId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(length = 10)
	private String pratoId;
    private Integer ingredienteId;
    private Integer porcaoId;
}