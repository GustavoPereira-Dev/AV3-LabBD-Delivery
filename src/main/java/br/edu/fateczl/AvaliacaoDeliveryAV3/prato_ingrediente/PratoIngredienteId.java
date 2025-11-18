package br.edu.fateczl.AvaliacaoDeliveryAV3.prato_ingrediente;

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
	private Integer pratoId;
    private Integer ingredienteId;
    private Integer porcaoId;
}