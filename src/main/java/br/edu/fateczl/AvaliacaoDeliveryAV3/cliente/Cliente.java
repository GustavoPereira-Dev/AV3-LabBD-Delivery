package br.edu.fateczl.AvaliacaoDeliveryAV3.cliente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

import br.edu.fateczl.AvaliacaoDeliveryAV3.pedido.Pedido;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "cpf")
public class Cliente {

    @Id
    @Column(length = 11)
    private String cpf;
    
    private String nome;
    private String telefone;
    private String endereco;
    private int numero;
    private String cep;
    
    @Column(name = "pontoreferencia")
    private String pontoReferencia;

    @OneToMany(mappedBy = "cliente")
    private Set<Pedido> pedidos;

    // Construtor para DTO
    public Cliente(AtualizacaoCliente dto) {
        this.cpf = dto.cpf();
        this.nome = dto.nome();
        this.telefone = dto.telefone();
        this.endereco = dto.endereco();
        this.numero = dto.numero();
        this.cep = dto.cep();
        this.pontoReferencia = dto.pontoReferencia();
    }

    public void atualizarInformacoes(AtualizacaoCliente dto) {
        if (dto.nome() != null) this.nome = dto.nome();
        if (dto.telefone() != null) this.telefone = dto.telefone();
        if (dto.endereco() != null) this.endereco = dto.endereco();
        if (dto.numero() != 0) this.numero = dto.numero();
        if (dto.cep() != null) this.cep = dto.cep();
        if (dto.pontoReferencia() != null) this.pontoReferencia = dto.pontoReferencia();
    }
}