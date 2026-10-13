package com.veltrix.dto.checkout;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CheckoutRequest {

    @NotNull(message = "La lista de productos es obligatoria")
    @NotEmpty(message = "La lista de productos no puede estar vacía")
    @Valid
    private List<ItemCheckoutDTO> productos;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;

    private String direccionEnvio; // Campo opcional enviado desde el checkout
}