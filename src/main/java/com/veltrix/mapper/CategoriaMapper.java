package com.veltrix.mapper;

import com.veltrix.dto.CategoriaDTO;
import com.veltrix.model.Categoria;

public class CategoriaMapper {

    public static CategoriaDTO toDTO(Categoria categoria) {

        CategoriaDTO dto = new CategoriaDTO();

        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());

        return dto;
    }
}