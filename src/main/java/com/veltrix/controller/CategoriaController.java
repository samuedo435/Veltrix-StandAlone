package com.veltrix.controller;

import com.veltrix.dto.CategoriaDTO;
import com.veltrix.mapper.CategoriaMapper;
import com.veltrix.model.Categoria;
import com.veltrix.service.CategoriaService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorías", description = "Operaciones para organizar el catálogo por categorías.")
@SecurityRequirement(name = "bearerAuth")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    @Operation(summary = "Listar categorías", description = "Obtiene todas las categorías registradas.")
    @ApiResponse(responseCode = "200", description = "Categorías obtenidas correctamente")
    public List<CategoriaDTO> listarCategorias() {
        return categoriaService.listarTodas()
                .stream()
                .map(CategoriaMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
        @Operation(summary = "Consultar categoría", description = "Obtiene una categoría por su identificador.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
            @ApiResponse(responseCode = "404", description = "La categoría no existe")
        })
    public CategoriaDTO obtenerCategoria(@PathVariable Long id) {
        return CategoriaMapper.toDTO( categoriaService.obtenerPorId(id));
    }

    @PostMapping
        @Operation(summary = "Crear categoría", description = "Registra una nueva categoría para el catálogo.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos")
        })
    public CategoriaDTO guardarCategoria(@Valid @RequestBody Categoria categoria) {
        return CategoriaMapper.toDTO( categoriaService.guardar(categoria));
    }

    @PutMapping("/{id}")
        @Operation(summary = "Actualizar categoría", description = "Modifica los datos de una categoría existente.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos"),
            @ApiResponse(responseCode = "404", description = "La categoría no existe")
        })
    public CategoriaDTO actualizarCategoria(
            @PathVariable Long id,
            @Valid @RequestBody Categoria categoria) {

        return CategoriaMapper.toDTO( categoriaService.actualizar(id, categoria));
    }

    @DeleteMapping("/{id}")
        @Operation(summary = "Eliminar categoría", description = "Elimina una categoría del catálogo.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "La categoría no existe")
        })
    public void eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminar(id);
    }
}