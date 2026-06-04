package com.veltrix.controller;

import com.veltrix.model.DetallePedido;
import com.veltrix.service.DetallePedidoService;
import org.springframework.web.bind.annotation.*;

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
    public List<DetallePedido> listarDetalles() {
        return detallePedidoService.listarTodos();
    }

    @GetMapping("/{id}")
    public DetallePedido obtenerDetalle(
            @PathVariable Long id) {

        return detallePedidoService.obtenerPorId(id);
    }

    @PostMapping
    public DetallePedido guardarDetalle(
            @RequestBody DetallePedido detallePedido) {

        return detallePedidoService.guardar(detallePedido);
    }

    @PutMapping("/{id}")
    public DetallePedido actualizarDetalle(
            @PathVariable Long id,
            @RequestBody DetallePedido detallePedido) {

        return detallePedidoService.actualizar(
                id,
                detallePedido);
    }

    @DeleteMapping("/{id}")
    public void eliminarDetalle(
            @PathVariable Long id) {

        detallePedidoService.eliminar(id);
    }
}