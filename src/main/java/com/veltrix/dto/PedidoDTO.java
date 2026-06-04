package com.veltrix.dto;

import com.veltrix.enums.EstadoPedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PedidoDTO {

    private Long id;
    private LocalDateTime fechaPedido;
    private Double montoTotal;
    private EstadoPedido estado;

    private Long clienteId;
    private String nombreCliente;

}