package com.veltrix.service;

import com.veltrix.exception.ResourceNotFoundException;
import com.veltrix.model.Pago;
import com.veltrix.repository.PagoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    public Pago guardar(Pago pago) {
        return pagoRepository.save(pago);
    }

    public List<Pago> listarTodos() {
        return pagoRepository.findAll();
    }

    public Pago obtenerPorId(Long id) {
        return pagoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Pago no encontrado con id: " + id));
    }

    public Pago actualizar(Long id, Pago pagoActualizado) {

        Pago Pago = obtenerPorId(id);

        Pago.setFechaPago(pagoActualizado.getFechaPago());
        Pago.setMonto(pagoActualizado.getMonto());
        Pago.setMetodoPago(pagoActualizado.getMetodoPago());
        Pago.setEstadoPago(pagoActualizado.getEstadoPago());
        Pago.setPedido(pagoActualizado.getPedido());

        return pagoRepository.save(Pago);
    }

    public void eliminar(Long id) {

        Pago Pago = obtenerPorId(id);

        pagoRepository.delete(Pago);
    }
}