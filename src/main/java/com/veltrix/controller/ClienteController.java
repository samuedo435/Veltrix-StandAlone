package com.veltrix.controller;

import com.veltrix.dto.ClienteDTO;
import com.veltrix.mapper.ClienteMapper;
import com.veltrix.model.Cliente;
import com.veltrix.service.ClienteService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Operaciones para administrar clientes.")
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    @Operation(summary = "Listar clientes", description = "Obtiene todos los clientes registrados.")
    @ApiResponse(responseCode = "200", description = "Clientes obtenidos correctamente")
    public List<ClienteDTO> listarClientes() {
        return clienteService.listarTodos()
                .stream()
                .map(ClienteMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
        @Operation(summary = "Consultar cliente", description = "Obtiene un cliente por su identificador.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "El cliente no existe")
        })
    public ClienteDTO obtenerCliente(@PathVariable Long id) {
        return ClienteMapper.toDTO( clienteService.obtenerPorId(id));
    }

    @PostMapping
        @Operation(summary = "Crear cliente", description = "Registra un nuevo cliente.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos")
        })
    public ClienteDTO guardarCliente(@Valid @RequestBody Cliente cliente) {
        return ClienteMapper.toDTO( clienteService.guardar(cliente));
    }

    @PutMapping("/{id}")
        @Operation(summary = "Actualizar cliente", description = "Modifica los datos de un cliente existente.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos"),
            @ApiResponse(responseCode = "404", description = "El cliente no existe")
        })
    public ClienteDTO actualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody Cliente cliente) {

        return ClienteMapper.toDTO( clienteService.actualizar(id, cliente));
    }

    @DeleteMapping("/{id}")
        @Operation(summary = "Eliminar cliente", description = "Elimina un cliente por su identificador.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "El cliente no existe")
        })
    public void eliminarCliente(@PathVariable Long id) {
        clienteService.eliminar(id);
    }
}