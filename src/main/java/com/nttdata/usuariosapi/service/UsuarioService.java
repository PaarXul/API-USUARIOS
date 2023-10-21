package com.nttdata.usuariosapi.service;

import com.nttdata.usuariosapi.dto.UsuarioDto;
import com.nttdata.usuariosapi.dto.UsuarioResponseDto;
import com.nttdata.usuariosapi.exceptions.CustomException;
import com.nttdata.usuariosapi.model.Usuario;

import java.util.Set;

public interface UsuarioService {

    UsuarioResponseDto guardarUsuario(UsuarioDto usuario) throws Exception, CustomException;

    Usuario obtenerUsuario(String usuarioId) throws CustomException;

    void eliminarUsuario(String usuarioId);

    //listar todos los usuarios
    Set<Usuario> obtenerUsuarios();

    UsuarioResponseDto actualizarUsuario(UsuarioDto usuario, String usuarioId) throws Exception, CustomException;


}
