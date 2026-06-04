package com.veltrix.mapper;

import com.veltrix.dto.PedidoDTO;
import com.veltrix.model.Pedido;

public class PedidoMapper {

    public static PedidoDTO toDTO(Pedido pedido) {

        PedidoDTO dto = new PedidoDTO();

        dto.setId(pedido.getId());
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setMontoTotal(pedido.getMontoTotal());
        dto.setEstado(pedido.getEstado());

        dto.setClienteId(
                pedido.getCliente().getId()
        );

        dto.setNombreCliente(
                pedido.getCliente().getNombre()
        );

        return dto;
    }
}