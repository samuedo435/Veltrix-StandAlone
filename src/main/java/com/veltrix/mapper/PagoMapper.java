package com.veltrix.mapper;

import com.veltrix.dto.PagoDTO;
import com.veltrix.model.Pago;

public class PagoMapper {

    public static PagoDTO toDTO(Pago pago) {

        PagoDTO dto = new PagoDTO();

        dto.setId(pago.getId());
        dto.setFechaPago(pago.getFechaPago());
        dto.setMonto(pago.getMonto());
        dto.setMetodoPago(pago.getMetodoPago());
        dto.setEstadoPago(pago.getEstadoPago());

        if (pago.getPedido() != null) {
            dto.setPedidoId(
                    pago.getPedido().getId()
            );
        }

        return dto;
    }
}