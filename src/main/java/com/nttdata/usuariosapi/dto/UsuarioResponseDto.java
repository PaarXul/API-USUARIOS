package com.nttdata.usuariosapi.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UsuarioResponseDto {
    private String id;
    private Date creado;
    private Date modificado;
    private Date ultimoLogin;
    private String token;
    private boolean activo;
}
