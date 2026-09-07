package com.veltrix.controller;

import com.veltrix.dto.UsuarioDTO;
import com.veltrix.mapper.UsuarioMapper;
import com.veltrix.model.Usuario;
import com.veltrix.service.UsuarioService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Operaciones para administrar usuarios y roles.")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Obtiene todos los usuarios registrados.")
    @ApiResponse(responseCode = "200", description = "Usuarios obtenidos correctamente")
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.listarTodos()
                .stream()
                .map(UsuarioMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
        @Operation(summary = "Consultar usuario", description = "Obtiene un usuario por su identificador.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "El usuario no existe")
        })
    public UsuarioDTO obtenerUsuario(@PathVariable Long id) {
        return UsuarioMapper.toDTO( usuarioService.obtenerPorId(id));
    }

    @PostMapping
        @Operation(summary = "Crear usuario", description = "Registra un usuario con sus datos y rol.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos")
        })
    public UsuarioDTO guardarUsuario(@Valid @RequestBody Usuario usuario) {
        return UsuarioMapper.toDTO( usuarioService.guardar(usuario));
    }

    @PutMapping("/{id}")
        @Operation(summary = "Actualizar usuario", description = "Modifica los datos de un usuario existente.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos"),
            @ApiResponse(responseCode = "404", description = "El usuario no existe")
        })
    public UsuarioDTO actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody Usuario usuario) {

        return UsuarioMapper.toDTO( usuarioService.actualizar(id, usuario));
    }

    @DeleteMapping("/{id}")
        @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por su identificador.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "El usuario no existe")
        })
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminar(id);
    }
}