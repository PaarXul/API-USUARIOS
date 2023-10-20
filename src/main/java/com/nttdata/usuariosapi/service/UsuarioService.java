package com.nttdata.usuariosapi.service;

import com.nttdata.usuariosapi.exceptions.CustomException;
import com.nttdata.usuariosapi.model.Usuario;

import java.util.Set;
import java.util.UUID;

public interface UsuarioService {

    Usuario guardarUsuario(Usuario usuario) throws Exception;

    Usuario obtenerUsuario(String usuarioId);

    void eliminarUsuario(String usuarioId);

    //listar todos los usuarios
    Set<Usuario> obtenerUsuarios();

    Usuario actualizarUsuario(Usuario usuario, String usuarioId) throws Exception, CustomException;


}
