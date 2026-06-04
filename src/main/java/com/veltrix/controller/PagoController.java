package com.veltrix.controller;

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
    public List<Pago> listarPagos() {
        return pagoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Pago obtenerPago(@PathVariable Long id) {
        return pagoService.obtenerPorId(id);
    }

    @PostMapping
    public Pago guardarPago(@RequestBody Pago pago) {
        return pagoService.guardar(pago);
    }

    @PutMapping("/{id}")
    public Pago actualizarPago(
            @PathVariable Long id,
            @RequestBody Pago pago) {

        return pagoService.actualizar(id, pago);
    }

    @DeleteMapping("/{id}")
    public void eliminarPago(@PathVariable Long id) {
        pagoService.eliminar(id);
    }
}