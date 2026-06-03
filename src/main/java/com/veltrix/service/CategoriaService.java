package com.veltrix.service;

import com.veltrix.model.Categoria;
import com.veltrix.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestionar categorías.
 */
@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * Guarda una categoría.
     */
    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    /**
     * Obtiene todas las categorías.
     */
    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }
}