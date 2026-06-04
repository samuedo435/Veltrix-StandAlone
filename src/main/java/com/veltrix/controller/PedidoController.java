package com.veltrix.controller;

import com.veltrix.model.Pedido;
import com.veltrix.service.PedidoService;
import com.veltrix.dto.PedidoDTO;
import com.veltrix.mapper.PedidoMapper;
import org.springframework.security.core.parameters.P;
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
    public List<PedidoDTO> listarPedidos() {

        return pedidoService.listarTodos()
                .stream()
                .map(PedidoMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public PedidoDTO obtenerPedido(
            @PathVariable Long id) {

        return PedidoMapper.toDTO(
                pedidoService.obtenerPorId(id)
        );
    }

    @PostMapping
    public PedidoDTO guardarPedido(
            @RequestBody Pedido pedido) {

        return PedidoMapper.toDTO( pedidoService.guardar(pedido));
    }

    @PutMapping("/{id}")
    public PedidoDTO actualizarPedido(
            @PathVariable Long id,
            @RequestBody Pedido pedido) {

        return PedidoMapper.toDTO( pedidoService.actualizar(id, pedido));
    }

    @DeleteMapping("/{id}")
    public void eliminarPedido(
            @PathVariable Long id) {

        pedidoService.eliminar(id);
    }
}