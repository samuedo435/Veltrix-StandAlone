package com.veltrix.security.auth;

import com.veltrix.dto.auth.AuthUserResponse;
import com.veltrix.dto.auth.RegisterRequest;
import com.veltrix.enums.Rol;
import com.veltrix.exception.DuplicateResourceException;
import com.veltrix.exception.InvalidCredentialsException;
import com.veltrix.exception.ResourceNotFoundException;
import com.veltrix.model.Cliente;
import com.veltrix.model.Usuario;
import com.veltrix.repository.ClienteRepository;
import com.veltrix.repository.UsuarioRepository;
import com.veltrix.security.jwt.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @org.springframework.beans.factory.annotation.Autowired
    public AuthService(
            UsuarioRepository usuarioRepository,
            ClienteRepository clienteRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthService(
            UsuarioRepository usuarioRepository,
            ClienteRepository clienteRepository,
            PasswordEncoder passwordEncoder) {

        this(usuarioRepository, clienteRepository, passwordEncoder, null);
    }

    public void register(RegisterRequest request) {

        String email = request.getEmail() != null ? request.getEmail() : request.getCorreo();

        if (usuarioRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();

        usuario.setCorreo(email);

        usuario.setPassword(
                passwordEncoder.encode(
                        request.getPassword()));

        usuario.setRol(Rol.CLIENTE);

        Usuario usuarioGuardado =
                usuarioRepository.save(usuario);

        Cliente cliente = new Cliente();

        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setTelefono(request.getTelefono() != null && !request.getTelefono().isBlank()
                ? request.getTelefono()
                : "N/A");
        cliente.setDireccion(request.getDireccion() != null && !request.getDireccion().isBlank()
                ? request.getDireccion()
                : "N/A");

        cliente.setUsuario(usuarioGuardado);

        clienteRepository.save(cliente);
    }

    public LoginResponse login(LoginRequest request) {
        String email = request.getEmail() != null ? request.getEmail() : request.getCorreo();
        return login(email, request.getPassword());
    }

    public LoginResponse login(String email, String rawPassword) {
        Usuario usuario = usuarioRepository
                .findByCorreo(email)
                .orElseThrow(() ->
                        new InvalidCredentialsException("Credenciales inválidas"));

        if (!passwordEncoder.matches(rawPassword, usuario.getPassword())) {
            throw new InvalidCredentialsException("Credenciales inválidas");
        }

        String token = jwtService != null
                ? jwtService.generarToken(usuario.getCorreo(), usuario.getRol().name())
                : null;

        return new LoginResponse(token);
    }

    public AuthUserResponse obtenerUsuarioActual(String correo) {

        Usuario usuario = usuarioRepository
                .findByCorreo(correo)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuario no encontrado"));

        return new AuthUserResponse(
                usuario.getId(),
                usuario.getCorreo(),
                usuario.getRol()
        );
    }
}