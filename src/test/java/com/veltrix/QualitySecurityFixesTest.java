package com.veltrix;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veltrix.dto.auth.RegisterRequest;
import com.veltrix.dto.checkout.CheckoutRequest;
import com.veltrix.security.auth.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.nio.file.Files;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class QualitySecurityFixesTest {

    @Autowired
    private WebApplicationContext context;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("QA-SEC-01: .env.example exists and contains DB_PASSWORD and JWT_SECRET")
    void testEnvExampleExists() throws Exception {
        File envExample = new File(".env.example");
        assertThat(envExample).exists();
        String content = Files.readString(envExample.toPath());
        assertThat(content).contains("DB_PASSWORD");
        assertThat(content).contains("JWT_SECRET");
    }

    @Test
    @DisplayName("QA-GIT-01: .gitignore contains *.trace.db and *.lock.db")
    void testGitignoreContainsRules() throws Exception {
        File gitignore = new File(".gitignore");
        assertThat(gitignore).exists();
        String content = Files.readString(gitignore.toPath());
        assertThat(content).contains("*.trace.db");
        assertThat(content).contains("*.lock.db");
    }

    @Test
    @DisplayName("QA-VAL-01: Register endpoint returns 400 when fields are invalid")
    void testRegisterValidationFailure() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setNombre(""); // blank
        request.setApellido(""); // blank
        request.setEmail("correo-invalido"); // invalid email
        request.setPassword("123"); // < 8 characters

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.errors.nombre").exists())
                .andExpect(jsonPath("$.errors.apellido").exists())
                .andExpect(jsonPath("$.errors.email").exists())
                .andExpect(jsonPath("$.errors.password").exists());
    }

    @Test
    @DisplayName("QA-VAL-01: Login endpoint returns 400 when fields are invalid")
    void testLoginValidationFailure() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("not-an-email");
        request.setPassword("");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.errors.email").exists())
                .andExpect(jsonPath("$.errors.password").exists());
    }

    @Test
    @WithMockUser(roles = "CLIENTE")
    @DisplayName("QA-VAL-01: Checkout endpoint returns 400 when items list is empty or payment method is blank")
    void testCheckoutValidationFailure() throws Exception {
        CheckoutRequest request = new CheckoutRequest();
        request.setProductos(Collections.emptyList());
        request.setMetodoPago("");

        mockMvc.perform(post("/api/pedidos/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.errors.productos").exists())
                .andExpect(jsonPath("$.errors.metodoPago").exists());
    }

    @Test
    @DisplayName("QA-EXC-01: Registering with existing email returns 409 Conflict")
    void testDuplicateResourceExceptionHandling() throws Exception {
        // First register a unique user
        String email = "qa_duplicate_" + System.currentTimeMillis() + "@test.com";
        RegisterRequest request = new RegisterRequest();
        request.setNombre("Test");
        request.setApellido("User");
        request.setEmail(email);
        request.setPassword("passwordSeguro123");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Attempt to register again with same email
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message").value("El email ya está registrado"));
    }

    @Test
    @DisplayName("QA-EXC-01: Login with invalid credentials returns 401 Unauthorized")
    void testInvalidCredentialsExceptionHandling() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("inexistente@test.com");
        request.setPassword("passwordIncorrecto123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.error").value("Unauthorized"))
                .andExpect(jsonPath("$.message").value("Credenciales inválidas"));
    }
}
