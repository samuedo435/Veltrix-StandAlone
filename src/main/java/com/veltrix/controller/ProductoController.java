package com.veltrix.controller;

import com.veltrix.dto.ProductoDTO;
import com.veltrix.mapper.ProductoMapper;
import com.veltrix.model.Producto;
import com.veltrix.service.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<ProductoDTO> listarProductos() {
        return productoService.listarTodos()
                .stream()
                .map(ProductoMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductoDTO obtenerProducto(@PathVariable Long id) {
        return ProductoMapper.toDTO( productoService.obtenerPorId(id));
    }

    @PostMapping
    public ProductoDTO guardarProducto(@RequestBody Producto producto) {
        return ProductoMapper.toDTO( productoService.guardar(producto));
    }

    @PutMapping("/{id}")
    public ProductoDTO actualizarProducto(
            @PathVariable Long id,
            @RequestBody Producto producto) {

        return ProductoMapper.toDTO( productoService.actualizar(id, producto));
    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminar(id);
    }
}