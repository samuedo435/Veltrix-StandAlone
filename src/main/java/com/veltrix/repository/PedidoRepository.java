package com.veltrix.repository;

import com.veltrix.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para operaciones CRUD de pedidos.
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}