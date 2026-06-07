package com.veltrix.security.auth;

import com.veltrix.dto.auth.AuthUserResponse;
import com.veltrix.dto.auth.RegisterRequest;
import com.veltrix.dto.auth.RegisterResponse;
import com.veltrix.model.Usuario;
import com.veltrix.repository.UsuarioRepository;
import com.veltrix.security.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthService authService;

    public AuthController(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthService authService) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        authService.register(request);

        return ResponseEntity.ok(
                new RegisterResponse(
                        "Usuario registrado correctamente"));
    }
    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request) {

        Usuario usuario = usuarioRepository
                .findByCorreo(request.getCorreo())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "Credenciales inválidas"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                usuario.getPassword())) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Credenciales inválidas");
        }

        String token =
                jwtService.generarToken(
                        usuario.getCorreo(),
                        usuario.getRol().name());

        return new LoginResponse(token);
    }
    @GetMapping("/me")
    public ResponseEntity<AuthUserResponse> me(
            Authentication authentication) {

        String correo = authentication.getName();

        return ResponseEntity.ok(
                authService.obtenerUsuarioActual(correo)
        );
    }
}