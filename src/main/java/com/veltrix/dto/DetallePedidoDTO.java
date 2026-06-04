package com.veltrix.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetallePedidoDTO {

    private Long id;

    private Integer cantidad;
    private Double subtotal;

    private Long pedidoId;

    private Long productoId;
    private String productoNombre;

}