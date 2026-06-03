package com.veltrix.service;

import com.veltrix.model.Cliente;
import com.veltrix.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestionar categorías.
 */
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Guarda una categoría.
     */
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /**
     * Obtiene todas las categorías.
     */
    public List<Cliente> listarTodas() {
        return clienteRepository.findAll();
    }
}