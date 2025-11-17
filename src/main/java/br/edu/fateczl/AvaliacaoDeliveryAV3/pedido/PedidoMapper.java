package br.edu.fateczl.AvaliacaoDeliveryAV3.pedido;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import br.edu.fateczl.AvaliacaoDeliveryAV3.cliente.Cliente;
import br.edu.fateczl.AvaliacaoDeliveryAV3.porcao.Porcao;
import br.edu.fateczl.AvaliacaoDeliveryAV3.prato.Prato;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    // Entity para DTO
    @Mapping(target = "clienteCpf", source = "cliente.cpf")
    @Mapping(target = "pratoId", source = "prato.id")
    @Mapping(target = "porcaoId", source = "porcao.id")
    AtualizacaoPedido toAtualizacaoDto(Pedido pedido);

    // DTO para Entity (Criação)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", source = "clienteCpf", qualifiedByName = "cpfToCliente")
    @Mapping(target = "prato", source = "pratoId", qualifiedByName = "idToPrato")
    @Mapping(target = "porcao", source = "porcaoId", qualifiedByName = "idToPorcao")
    Pedido toEntityFromAtualizacao(AtualizacaoPedido dto);

    // Atualiza Entity com DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", source = "clienteCpf", qualifiedByName = "cpfToCliente")
    @Mapping(target = "prato", source = "pratoId", qualifiedByName = "idToPrato")
    @Mapping(target = "porcao", source = "porcaoId", qualifiedByName = "idToPorcao")
    void updateEntityFromDto(AtualizacaoPedido dto, @MappingTarget Pedido pedido);

    // --- Qualificadores ---
    
    @Named("cpfToCliente")
    default Cliente cpfToCliente(String cpf) {
        if (cpf == null) return null;
        Cliente cliente = new Cliente();
        cliente.setCpf(cpf);
        return cliente;
    }

    @Named("idToPrato")
    default Prato idToPrato(Integer pratoId) {
        if (pratoId == null) return null;
        Prato prato = new Prato();
        prato.setId(pratoId);
        return prato;
    }

    @Named("idToPorcao")
    default Porcao idToPorcao(Integer porcaoId) {
        if (porcaoId == null) return null;
        Porcao porcao = new Porcao();
        porcao.setId(porcaoId);
        return porcao;
    }
}