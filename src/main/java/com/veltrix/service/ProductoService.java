package com.veltrix.service;

import com.veltrix.exception.ResourceNotFoundException;
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

    public Producto obtenerPorId(Long id){
        return productoRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Producto no encontrado"));
    }

    public Producto actualizar(Long id, Producto productoActualizado) {

        Producto producto = obtenerPorId(id);

        producto.setNombre(productoActualizado.getNombre());
        producto.setDescripcion(productoActualizado.getDescripcion());
        producto.setPrecio(productoActualizado.getPrecio());
        producto.setStock(productoActualizado.getStock());
        producto.setCategoria(productoActualizado.getCategoria());

        return productoRepository.save(producto);
    }

    public void eliminar(Long id) {

        Producto producto = obtenerPorId(id);

        productoRepository.delete(producto);
    }
}