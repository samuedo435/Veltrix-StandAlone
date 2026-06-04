package com.veltrix.controller;

import com.veltrix.dto.PagoDTO;
import com.veltrix.mapper.PagoMapper;
import com.veltrix.model.Pago;
import com.veltrix.service.PagoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    public List<PagoDTO> listarPagos() {
        return pagoService.listarTodos()
                .stream()
                .map(PagoMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public PagoDTO obtenerPago(@PathVariable Long id) {
        return PagoMapper.toDTO( pagoService.obtenerPorId(id));
    }

    @PostMapping
    public PagoDTO guardarPago(@RequestBody Pago pago) {
        return PagoMapper.toDTO( pagoService.guardar(pago));
    }

    @PutMapping("/{id}")
    public PagoDTO actualizarPago(
            @PathVariable Long id,
            @RequestBody Pago pago) {

        return PagoMapper.toDTO( pagoService.actualizar(id, pago));
    }

    @DeleteMapping("/{id}")
    public void eliminarPago(@PathVariable Long id) {
        pagoService.eliminar(id);
    }
}