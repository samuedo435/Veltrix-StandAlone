package com.veltrix.service;

import com.veltrix.exception.ResourceNotFoundException;
import com.veltrix.model.Pedido;
import com.veltrix.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido guardar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido obtenerPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Pedido no encontrado con id: " + id));
    }

    public Pedido actualizar(Long id, Pedido pedidoActualizado) {

        Pedido pedido = obtenerPorId(id);

        pedido.setFechaPedido(pedidoActualizado.getFechaPedido());
        pedido.setMontoTotal(pedidoActualizado.getMontoTotal());
        pedido.setEstado(pedidoActualizado.getEstado());
        pedido.setCliente(pedidoActualizado.getCliente());

        return pedidoRepository.save(pedido);
    }

    public void eliminar(Long id) {

        Pedido pedido = obtenerPorId(id);

        pedidoRepository.delete(pedido);
    }
}