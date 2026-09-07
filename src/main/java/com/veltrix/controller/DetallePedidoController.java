package com.veltrix.controller;

import com.veltrix.mapper.DetallePedidoMapper;
import com.veltrix.dto.DetallePedidoDTO;
import com.veltrix.model.DetallePedido;
import com.veltrix.service.DetallePedidoService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/detalles-pedido")
@Tag(name = "Detalles de pedido", description = "Operaciones para administrar los artículos incluidos en los pedidos.")
@SecurityRequirement(name = "bearerAuth")
public class DetallePedidoController {

    private final DetallePedidoService detallePedidoService;

    public DetallePedidoController(
            DetallePedidoService detallePedidoService) {

        this.detallePedidoService = detallePedidoService;
    }

    @GetMapping
    @Operation(summary = "Listar detalles", description = "Obtiene todos los detalles de pedido registrados.")
    @ApiResponse(responseCode = "200", description = "Detalles obtenidos correctamente")
    public List<DetallePedidoDTO> listarDetalles() {
        return detallePedidoService.listarTodos()
                .stream()
                .map(DetallePedidoMapper::toDTO)
                .toList();
        }

    @GetMapping("/{id}")
        @Operation(summary = "Consultar detalle", description = "Obtiene un detalle de pedido por su identificador.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalle encontrado"),
            @ApiResponse(responseCode = "404", description = "El detalle no existe")
        })
    public DetallePedidoDTO obtenerDetalle(
            @PathVariable Long id) {

        return DetallePedidoMapper.toDTO( detallePedidoService.obtenerPorId(id));
    }

    @PostMapping
        @Operation(summary = "Crear detalle", description = "Añade un artículo a un pedido.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalle creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos")
        })
    public DetallePedidoDTO guardarDetalle(
            @Valid @RequestBody DetallePedido detallePedido) {

        return DetallePedidoMapper.toDTO( detallePedidoService.guardar(detallePedido));
    }

    @PutMapping("/{id}")
        @Operation(summary = "Actualizar detalle", description = "Modifica un artículo incluido en un pedido.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalle actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos"),
            @ApiResponse(responseCode = "404", description = "El detalle no existe")
        })
    public DetallePedidoDTO actualizarDetalle(
            @PathVariable Long id,
            @Valid @RequestBody DetallePedido detallePedido) {

        return DetallePedidoMapper.toDTO( detallePedidoService.actualizar(
                id,
                detallePedido));
    }

    @DeleteMapping("/{id}")
        @Operation(summary = "Eliminar detalle", description = "Elimina un artículo de un pedido.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalle eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "El detalle no existe")
        })
    public void eliminarDetalle(
            @PathVariable Long id) {

        detallePedidoService.eliminar(id);
    }
}