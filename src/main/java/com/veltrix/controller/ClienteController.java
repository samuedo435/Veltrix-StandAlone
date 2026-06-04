package com.veltrix.controller;

import com.veltrix.dto.ClienteDTO;
import com.veltrix.mapper.ClienteMapper;
import com.veltrix.model.Cliente;
import com.veltrix.service.ClienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<ClienteDTO> listarClientes() {
        return clienteService.listarTodos()
                .stream()
                .map(ClienteMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ClienteDTO obtenerCliente(@PathVariable Long id) {
        return ClienteMapper.toDTO( clienteService.obtenerPorId(id));
    }

    @PostMapping
    public ClienteDTO guardarCliente(@RequestBody Cliente cliente) {
        return ClienteMapper.toDTO( clienteService.guardar(cliente));
    }

    @PutMapping("/{id}")
    public ClienteDTO actualizarCliente(
            @PathVariable Long id,
            @RequestBody Cliente cliente) {

        return ClienteMapper.toDTO( clienteService.actualizar(id, cliente));
    }

    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable Long id) {
        clienteService.eliminar(id);
    }
}