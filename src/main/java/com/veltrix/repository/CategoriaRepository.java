package com.veltrix.repository;

import com.veltrix.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para operaciones CRUD de categorías.
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}