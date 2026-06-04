package com.veltrix.dto;

import com.veltrix.enums.EstadoPago;
import com.veltrix.enums.MetodoPago;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PagoDTO {
    private Long id;

    private LocalDateTime fechaPago;
    private Double monto;
    private MetodoPago metodoPago;
    private EstadoPago estadoPago;

    private Long pedidoId;
}
