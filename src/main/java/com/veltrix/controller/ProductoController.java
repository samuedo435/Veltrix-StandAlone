package com.veltrix.controller;

import com.veltrix.dto.ProductoDTO;
import com.veltrix.mapper.ProductoMapper;
import com.veltrix.model.Producto;
import com.veltrix.service.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Operaciones para administrar el catálogo de productos.")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    @Operation(summary = "Listar productos", description = "Obtiene todos los productos disponibles en el catálogo.")
    @ApiResponse(responseCode = "200", description = "Productos obtenidos correctamente")
    public List<ProductoDTO> listarProductos() {
        return productoService.listarTodos()
                .stream()
                .map(ProductoMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
        @Operation(summary = "Consultar producto", description = "Obtiene un producto por su identificador.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "El producto no existe")
        })
    public ProductoDTO obtenerProducto(@PathVariable Long id) {
        return ProductoMapper.toDTO( productoService.obtenerPorId(id));
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
        @Operation(summary = "Crear producto", description = "Registra un nuevo producto en el catálogo.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos")
        })
    public ProductoDTO guardarProducto(@Valid @RequestBody Producto producto) {
        return ProductoMapper.toDTO( productoService.guardar(producto));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
        @Operation(summary = "Actualizar producto", description = "Modifica los datos de un producto existente.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos"),
            @ApiResponse(responseCode = "404", description = "El producto no existe")
        })
    public ProductoDTO actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody Producto producto) {

        return ProductoMapper.toDTO( productoService.actualizar(id, producto));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
        @Operation(summary = "Eliminar producto", description = "Elimina un producto del catálogo.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "El producto no existe")
        })
    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminar(id);
    }
}