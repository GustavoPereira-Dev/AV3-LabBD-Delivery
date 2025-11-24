package br.edu.fateczl.AvaliacaoDeliveryAV3.prato_ingrediente;

import br.edu.fateczl.AvaliacaoDeliveryAV3.ingrediente.Ingrediente;
import br.edu.fateczl.AvaliacaoDeliveryAV3.porcao.Porcao;
import br.edu.fateczl.AvaliacaoDeliveryAV3.prato.Prato;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "prato_ingrediente_porcao")
@Getter
@Setter
@NoArgsConstructor
public class PratoIngrediente {

    @EmbeddedId
    private PratoIngredienteId id = new PratoIngredienteId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("pratoId")
    @JoinColumn(name = "id_prato", columnDefinition = "varchar(10)", nullable = false)
    private Prato prato;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredienteId")
    @JoinColumn(name = "id_ingrediente", nullable = false)
    private Ingrediente ingrediente;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("porcaoId")
    @JoinColumn(name = "id_porcao", nullable = false)
    private Porcao porcao;

    public PratoIngrediente(Prato prato, Ingrediente ingrediente, Porcao porcao) {
        this.prato = prato;
        this.ingrediente = ingrediente;
        this.porcao = porcao;
        this.id = new PratoIngredienteId(prato.getId(), ingrediente.getId(), porcao.getId());
    }
}