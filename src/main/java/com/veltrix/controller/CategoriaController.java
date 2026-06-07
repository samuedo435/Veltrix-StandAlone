package com.veltrix.controller;

import com.veltrix.dto.CategoriaDTO;
import com.veltrix.mapper.CategoriaMapper;
import com.veltrix.model.Categoria;
import com.veltrix.service.CategoriaService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<CategoriaDTO> listarCategorias() {
        return categoriaService.listarTodas()
                .stream()
                .map(CategoriaMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public CategoriaDTO obtenerCategoria(@PathVariable Long id) {
        return CategoriaMapper.toDTO( categoriaService.obtenerPorId(id));
    }

    @PostMapping
    public CategoriaDTO guardarCategoria(@Valid @RequestBody Categoria categoria) {
        return CategoriaMapper.toDTO( categoriaService.guardar(categoria));
    }

    @PutMapping("/{id}")
    public CategoriaDTO actualizarCategoria(
            @PathVariable Long id,
            @Valid @RequestBody Categoria categoria) {

        return CategoriaMapper.toDTO( categoriaService.actualizar(id, categoria));
    }

    @DeleteMapping("/{id}")
    public void eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminar(id);
    }
}