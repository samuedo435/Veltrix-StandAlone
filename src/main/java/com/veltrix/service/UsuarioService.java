package com.veltrix.service;

import com.veltrix.model.Usuario;
import com.veltrix.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestionar categorías.
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Guarda una categoría.
     */
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Obtiene todas las categorías.
     */
    public List<Usuario> listarTodas() {
        return usuarioRepository.findAll();
    }
}