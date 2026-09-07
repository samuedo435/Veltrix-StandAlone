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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Registro de cuentas, emisión de tokens JWT y consulta de sesión.")
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
    @Operation(summary = "Registrar usuario", description = "Crea una cuenta nueva y devuelve un mensaje de confirmación.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos de registro no son válidos")
    })
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        authService.register(request);

        return ResponseEntity.ok(
                new RegisterResponse(
                        "Usuario registrado correctamente"));
    }
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Valida las credenciales y devuelve un token JWT para acceder a las operaciones protegidas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Credenciales válidas; token generado"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
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
    @Operation(summary = "Consultar sesión actual", description = "Obtiene la identidad y el rol asociados al token JWT autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario autenticado encontrado"),
            @ApiResponse(responseCode = "401", description = "Token ausente, inválido o expirado")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<AuthUserResponse> me(
            Authentication authentication) {

        String correo = authentication.getName();

        return ResponseEntity.ok(
                authService.obtenerUsuarioActual(correo)
        );
    }
}