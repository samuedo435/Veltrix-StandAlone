package com.veltrix.model;

import com.veltrix.enums.EstadoPedido;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime fechaPedido;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor que cero")
    private Double montoTotal;

    @NotNull(message = "El estado es obligatorio")
    private EstadoPedido estado;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

}