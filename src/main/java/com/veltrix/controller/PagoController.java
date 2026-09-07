package com.veltrix.controller;

import com.veltrix.dto.PagoDTO;
import com.veltrix.mapper.PagoMapper;
import com.veltrix.model.Pago;
import com.veltrix.service.PagoService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@Tag(name = "Pagos", description = "Operaciones para administrar pagos.")
@SecurityRequirement(name = "bearerAuth")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    @Operation(summary = "Listar pagos", description = "Obtiene todos los pagos registrados.")
    @ApiResponse(responseCode = "200", description = "Pagos obtenidos correctamente")
    public List<PagoDTO> listarPagos() {
        return pagoService.listarTodos()
                .stream()
                .map(PagoMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
        @Operation(summary = "Consultar pago", description = "Obtiene un pago por su identificador.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "El pago no existe")
        })
    public PagoDTO obtenerPago(@PathVariable Long id) {
        return PagoMapper.toDTO( pagoService.obtenerPorId(id));
    }

    @PostMapping
        @Operation(summary = "Crear pago", description = "Registra un nuevo pago.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos")
        })
    public PagoDTO guardarPago(@Valid @RequestBody Pago pago) {
        return PagoMapper.toDTO( pagoService.guardar(pago));
    }

    @PutMapping("/{id}")
        @Operation(summary = "Actualizar pago", description = "Modifica los datos de un pago existente.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos"),
            @ApiResponse(responseCode = "404", description = "El pago no existe")
        })
    public PagoDTO actualizarPago(
            @PathVariable Long id,
            @Valid @RequestBody Pago pago) {

        return PagoMapper.toDTO( pagoService.actualizar(id, pago));
    }

    @DeleteMapping("/{id}")
        @Operation(summary = "Eliminar pago", description = "Elimina un pago por su identificador.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "El pago no existe")
        })
    public void eliminarPago(@PathVariable Long id) {
        pagoService.eliminar(id);
    }
}