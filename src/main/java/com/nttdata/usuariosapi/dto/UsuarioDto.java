package com.nttdata.usuariosapi.dto;


import com.nttdata.usuariosapi.model.Telefonos;
import lombok.Data;

import java.util.Set;

@Data
public class UsuarioDto {

    private String id;
    private String nombre;
    private String correo;
    private String contrasena;
    private Set<Telefonos> telefonos;

}
