package com.veltrix.dto.auth;

import com.veltrix.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthUserResponse {

    private Long id;
    private String correo;
    private Rol rol;

}