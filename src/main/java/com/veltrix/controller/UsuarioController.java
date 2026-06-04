package com.veltrix.controller;

import com.veltrix.dto.UsuarioDTO;
import com.veltrix.mapper.UsuarioMapper;
import com.veltrix.model.Usuario;
import com.veltrix.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.listarTodos()
                .stream()
                .map(UsuarioMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public UsuarioDTO obtenerUsuario(@PathVariable Long id) {
        return UsuarioMapper.toDTO( usuarioService.obtenerPorId(id));
    }

    @PostMapping
    public UsuarioDTO guardarUsuario(@RequestBody Usuario usuario) {
        return UsuarioMapper.toDTO( usuarioService.guardar(usuario));
    }

    @PutMapping("/{id}")
    public UsuarioDTO actualizarUsuario(
            @PathVariable Long id,
            @RequestBody Usuario usuario) {

        return UsuarioMapper.toDTO( usuarioService.actualizar(id, usuario));
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminar(id);
    }
}