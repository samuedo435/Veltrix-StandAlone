package com.veltrix.dto.checkout;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemCheckoutDTO {

    private Long productoId;

    private Integer cantidad;
}