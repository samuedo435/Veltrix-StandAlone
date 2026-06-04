package com.veltrix.mapper;

import com.veltrix.dto.DetallePedidoDTO;
import com.veltrix.model.DetallePedido;

public class DetallePedidoMapper {

    public static DetallePedidoDTO toDTO(
            DetallePedido detalle) {

        DetallePedidoDTO dto =
                new DetallePedidoDTO();

        dto.setId(detalle.getId());
        dto.setCantidad(detalle.getCantidad());
        dto.setSubtotal(detalle.getSubtotal());

        dto.setPedidoId(
                detalle.getPedido().getId()
        );

        dto.setProductoId(
                detalle.getProducto().getId()
        );

        dto.setProductoNombre(
                detalle.getProducto().getNombre()
        );

        return dto;
    }
}