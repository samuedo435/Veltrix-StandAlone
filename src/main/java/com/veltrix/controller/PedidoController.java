package com.veltrix.controller;

import com.veltrix.dto.checkout.CheckoutRequest;
import com.veltrix.dto.checkout.CheckoutResponse;
import com.veltrix.model.Pedido;
import com.veltrix.service.PedidoService;
import com.veltrix.dto.PedidoDTO;
import com.veltrix.mapper.PedidoMapper;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Operaciones para consultar y gestionar pedidos.")
@SecurityRequirement(name = "bearerAuth")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    @Operation(summary = "Listar pedidos", description = "Obtiene todos los pedidos registrados.")
    @ApiResponse(responseCode = "200", description = "Pedidos obtenidos correctamente")
    public List<PedidoDTO> listarPedidos() {

        return pedidoService.listarTodos()
                .stream()
                .map(PedidoMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
        @Operation(summary = "Consultar pedido", description = "Obtiene un pedido por su identificador.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "El pedido no existe")
        })
    public PedidoDTO obtenerPedido(
            @PathVariable Long id) {

        return PedidoMapper.toDTO(
                pedidoService.obtenerPorId(id)
        );
    }

    @PostMapping
        @Operation(summary = "Crear pedido", description = "Registra un pedido directamente con sus datos de dominio.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos")
        })
    public PedidoDTO guardarPedido(
            @Valid @RequestBody Pedido pedido) {

        return PedidoMapper.toDTO( pedidoService.guardar(pedido));
    }

    @PutMapping("/{id}")
        @Operation(summary = "Actualizar pedido", description = "Modifica los datos de un pedido existente.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos"),
            @ApiResponse(responseCode = "404", description = "El pedido no existe")
        })
    public PedidoDTO actualizarPedido(
            @PathVariable Long id,
            @Valid @RequestBody Pedido pedido) {

        return PedidoMapper.toDTO( pedidoService.actualizar(id, pedido));
    }

    @DeleteMapping("/{id}")
        @Operation(summary = "Eliminar pedido", description = "Elimina un pedido por su identificador.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "El pedido no existe")
        })
    public void eliminarPedido(
            @PathVariable Long id) {

        pedidoService.eliminar(id);
    }

    @PostMapping("/checkout")
        @Operation(summary = "Procesar checkout", description = "Crea un pedido a partir del carrito y la información de pago y envío.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Checkout procesado correctamente"),
            @ApiResponse(responseCode = "400", description = "El carrito o los datos de checkout no son válidos")
        })
    public CheckoutResponse checkout(
            @RequestBody CheckoutRequest request) {

        return pedidoService.realizarCheckout(request);
    }
}