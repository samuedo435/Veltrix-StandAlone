package com.veltrix.repository;

import com.veltrix.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para operaciones CRUD de detalles de pedidos.
 */
@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

}