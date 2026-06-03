package com.veltrix.service;

import com.veltrix.model.Usuario;
import com.veltrix.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestionar usuarios.
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Guarda un usuario.
     */
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Obtiene todos los usuarios.
     */
    public List<Usuario> listarTodas() {
        return usuarioRepository.findAll();
    }
}