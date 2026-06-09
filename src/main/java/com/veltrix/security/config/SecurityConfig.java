package com.veltrix.security.config;

import com.veltrix.exception.JwtAuthenticationEntryPoint;
import com.veltrix.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final com.veltrix.exception.JwtAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            JwtAuthenticationEntryPoint authenticationEntryPoint,
            com.veltrix.exception.JwtAccessDeniedHandler accessDeniedHandler) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})

                .headers(headers ->
                        headers.frameOptions(frame -> frame.disable())
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        // Públicos
                        .requestMatchers(
                                "/api/auth/**",
                                "/h2-console/**",
                                "/error",
                                "/swagger-ui/**",
                                "/swagger-ui.html",

                                "/v3/api-docs/**"
                        ).permitAll()

                        // Usuarios -> solo ADMIN
                        .requestMatchers("/api/usuarios/**")
                        .hasRole("ADMIN")

                        // Categorías -> solo ADMIN
                        .requestMatchers("/api/categorias/**")
                        .hasRole("ADMIN")

                        // Productos
                        .requestMatchers(HttpMethod.GET,
                                "/api/productos/**")
                        .hasAnyRole("ADMIN", "CLIENTE")

                        .requestMatchers(HttpMethod.POST,
                                "/api/productos/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT,
                                "/api/productos/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                "/api/productos/**")
                        .hasRole("ADMIN")

                        // Pedidos
                        .requestMatchers("/api/pedidos/**")
                        .hasAnyRole("ADMIN", "CLIENTE")

                        // Detalles de pedido
                        .requestMatchers("/api/detalles-pedido/**")
                        .hasAnyRole("ADMIN", "CLIENTE")

                        // Pagos
                        .requestMatchers("/api/pagos/**")
                        .hasAnyRole("ADMIN", "CLIENTE")


                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .exceptionHandling(exception -> exception

                        .authenticationEntryPoint(
                                authenticationEntryPoint)

                        .accessDeniedHandler(
                                accessDeniedHandler)
                );

        return http.build();
    }
}