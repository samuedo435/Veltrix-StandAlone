package com.veltrix.service;

import com.veltrix.exception.ResourceNotFoundException;
import com.veltrix.model.DetallePedido;
import com.veltrix.repository.DetallePedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetallePedidoService {

    private final DetallePedidoRepository detallePedidoRepository;

    public DetallePedidoService(DetallePedidoRepository detallePedidoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
    }

    public DetallePedido guardar(DetallePedido detallePedido) {
        return detallePedidoRepository.save(detallePedido);
    }

    public List<DetallePedido> listarTodos() {
        return detallePedidoRepository.findAll();
    }

    public DetallePedido obtenerPorId(Long id) {
        return detallePedidoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "DetallePedido no encontrado con id: " + id));
    }

    public DetallePedido actualizar(Long id, DetallePedido detallePedidoActualizado) {

        DetallePedido detallePedido = obtenerPorId(id);

        detallePedido.setCantidad(detallePedidoActualizado.getCantidad());
        detallePedido.setSubtotal(detallePedidoActualizado.getSubtotal());
        detallePedido.setPedido(detallePedidoActualizado.getPedido());
        detallePedido.setProducto(detallePedidoActualizado.getProducto());
        
        return detallePedidoRepository.save(detallePedido);
    }

    public void eliminar(Long id) {

        DetallePedido detallePedido = obtenerPorId(id);

        detallePedidoRepository.delete(detallePedido);
    }
}