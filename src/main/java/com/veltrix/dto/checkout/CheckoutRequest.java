package com.veltrix.dto.checkout;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CheckoutRequest {

    private List<ItemCheckoutDTO> productos;

    private String metodoPago;
}