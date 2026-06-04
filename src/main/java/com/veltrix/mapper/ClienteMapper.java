package com.veltrix.mapper;

import com.veltrix.dto.ClienteDTO;
import com.veltrix.model.Cliente;

public class ClienteMapper {

    public static ClienteDTO toDTO(Cliente cliente) {

        ClienteDTO dto = new ClienteDTO();

        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setTelefono(cliente.getTelefono());
        dto.setDireccion(cliente.getDireccion());

        if(cliente.getUsuario() != null){

            dto.setUsuarioId(
                    cliente.getUsuario().getId()
            );

            dto.setCorreo(
                    cliente.getUsuario().getCorreo()
            );
        }

        return dto;
    }
}