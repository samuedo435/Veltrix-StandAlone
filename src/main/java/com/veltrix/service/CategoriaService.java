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
    
    public Categoria obtenerPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    public Categoria actualizar(Long id, Categoria categoriaActualizada) {

        Categoria categoria = obtenerPorId(id);

        categoria.setNombre(categoriaActualizada.getNombre());
        categoria.setDescripcion(categoriaActualizada.getDescripcion());

        return categoriaRepository.save(categoria);
    }

    public void eliminar(Long id) {

        Categoria categoria = obtenerPorId(id);

        categoriaRepository.delete(categoria);
    }
}