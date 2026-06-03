package com.veltrix.service;

import com.veltrix.model.Producto;
import com.veltrix.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestionar productos.
 */
@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }
    /**
     * Crear productos
     */
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }
    /**
     * Obtiene todos los productos
     */
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }
}