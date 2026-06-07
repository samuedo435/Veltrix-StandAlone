package com.veltrix.model;

import com.veltrix.enums.EstadoPago;
import com.veltrix.enums.MetodoPago;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

/**
 * Entidad que representa el pago de un pedido.
 */
@Entity
@Table(name = "pagos")
@Getter
@Setter
@NoArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime fechaPago;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor que cero")
    private Double monto;

    @NotNull(message = "El método de pago es obligatorio")
    private MetodoPago metodoPago;

    @NotNull(message = "El estado del pago es obligatorio")
    private EstadoPago estadoPago;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

}