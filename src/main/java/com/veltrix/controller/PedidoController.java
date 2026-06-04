package com.veltrix.controller;

import com.veltrix.model.Pedido;
import com.veltrix.service.PedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Pedido obtenerPedido(@PathVariable Long id) {
        return pedidoService.obtenerPorId(id);
    }

    @PostMapping
    public Pedido guardarPedido(
            @RequestBody Pedido pedido) {

        return pedidoService.guardar(pedido);
    }

    @PutMapping("/{id}")
    public Pedido actualizarPedido(
            @PathVariable Long id,
            @RequestBody Pedido pedido) {

        return pedidoService.actualizar(id, pedido);
    }

    @DeleteMapping("/{id}")
    public void eliminarPedido(
            @PathVariable Long id) {

        pedidoService.eliminar(id);
    }
}