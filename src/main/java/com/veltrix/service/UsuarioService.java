package com.veltrix.service;

import com.veltrix.exception.ResourceNotFoundException;
import com.veltrix.model.Usuario;
import com.veltrix.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * Servicio para gestionar usuarios.
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario guardar(Usuario usuario) {

        usuario.setPassword(
                passwordEncoder.encode(usuario.getPassword())
        );

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuario no encontrado con id: " + id));
    }

    public Usuario actualizar(Long id, Usuario usuarioActualizado) {

        Usuario usuario = obtenerPorId(id);

        usuario.setCorreo(usuarioActualizado.getCorreo());

        usuario.setPassword(
                passwordEncoder.encode(
                        usuarioActualizado.getPassword()
                )
        );

        usuario.setRol(usuarioActualizado.getRol());

        return usuarioRepository.save(usuario);
    }

    public void eliminar(Long id) {

        Usuario usuario = obtenerPorId(id);

        usuarioRepository.delete(usuario);
    }
}