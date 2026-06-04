package com.veltrix.repository;

import com.veltrix.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para operaciones CRUD de pagos.
 */
@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

}