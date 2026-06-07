package com.veltrix.controller;

import com.veltrix.mapper.DetallePedidoMapper;
import com.veltrix.dto.DetallePedidoDTO;
import com.veltrix.model.DetallePedido;
import com.veltrix.service.DetallePedidoService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/detalles-pedido")
public class DetallePedidoController {

    private final DetallePedidoService detallePedidoService;

    public DetallePedidoController(
            DetallePedidoService detallePedidoService) {

        this.detallePedidoService = detallePedidoService;
    }

    @GetMapping
    public List<DetallePedidoDTO> listarDetalles() {
        return detallePedidoService.listarTodos()
                .stream()
                .map(DetallePedidoMapper::toDTO)
                .toList();
        }

    @GetMapping("/{id}")
    public DetallePedidoDTO obtenerDetalle(
            @PathVariable Long id) {

        return DetallePedidoMapper.toDTO( detallePedidoService.obtenerPorId(id));
    }

    @PostMapping
    public DetallePedidoDTO guardarDetalle(
            @Valid @RequestBody DetallePedido detallePedido) {

        return DetallePedidoMapper.toDTO( detallePedidoService.guardar(detallePedido));
    }

    @PutMapping("/{id}")
    public DetallePedidoDTO actualizarDetalle(
            @PathVariable Long id,
            @Valid @RequestBody DetallePedido detallePedido) {

        return DetallePedidoMapper.toDTO( detallePedidoService.actualizar(
                id,
                detallePedido));
    }

    @DeleteMapping("/{id}")
    public void eliminarDetalle(
            @PathVariable Long id) {

        detallePedidoService.eliminar(id);
    }
}