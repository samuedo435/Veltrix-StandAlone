package com.veltrix.repository;

import com.veltrix.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para operaciones CRUD de productos.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

}