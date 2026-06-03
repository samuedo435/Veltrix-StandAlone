package com.veltrix.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa un cliente.
 */
@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    private String telefono;

    private String direccion;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}