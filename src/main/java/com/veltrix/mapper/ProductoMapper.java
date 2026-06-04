package com.veltrix.mapper;

import com.veltrix.dto.ProductoDTO;
import com.veltrix.model.Producto;

public class ProductoMapper {

    public static ProductoDTO toDTO(Producto producto) {

        ProductoDTO dto = new ProductoDTO();

        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());

        if(producto.getCategoria() != null){

            dto.setCategoriaId(
                    producto.getCategoria().getId()
            );

            dto.setCategoriaNombre(
                    producto.getCategoria().getNombre()
            );
        }

        return dto;
    }
}