package com.veltrix.security.auth;

import com.veltrix.dto.auth.RegisterRequest;
import com.veltrix.enums.Rol;
import com.veltrix.model.Cliente;
import com.veltrix.model.Usuario;
import com.veltrix.repository.ClienteRepository;
import com.veltrix.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UsuarioRepository usuarioRepository,
            ClienteRepository clienteRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest request) {

        if (usuarioRepository.existsByCorreo(request.getCorreo())) {

            throw new RuntimeException(
                    "Ya existe un usuario con ese correo");
        }

        Usuario usuario = new Usuario();

        usuario.setCorreo(request.getCorreo());

        usuario.setPassword(
                passwordEncoder.encode(
                        request.getPassword()));

        usuario.setRol(Rol.CLIENTE);

        Usuario usuarioGuardado =
                usuarioRepository.save(usuario);

        Cliente cliente = new Cliente();

        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setTelefono(request.getTelefono());
        cliente.setDireccion(request.getDireccion());

        cliente.setUsuario(usuarioGuardado);

        clienteRepository.save(cliente);
    }
}