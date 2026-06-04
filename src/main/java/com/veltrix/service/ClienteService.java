package com.veltrix.service;

import com.veltrix.exception.ResourceNotFoundException;
import com.veltrix.model.Cliente;
import com.veltrix.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestionar clientes.
 */
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Guarda un cliente.
     */
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /**
     * Obtiene todos los clientes.
     */
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cliente no encontrado con id: " + id));
    }

    public Cliente actualizar(Long id, Cliente clienteActualizado) {

        Cliente cliente = obtenerPorId(id);

        cliente.setNombre(clienteActualizado.getNombre());
        cliente.setApellido(clienteActualizado.getApellido());
        cliente.setTelefono(clienteActualizado.getTelefono());
        cliente.setDireccion(clienteActualizado.getDireccion());
        cliente.setUsuario(clienteActualizado.getUsuario());

        return clienteRepository.save(cliente);
    }

    public void eliminar(Long id) {

        Cliente cliente = obtenerPorId(id);

        clienteRepository.delete(cliente);
    }
}