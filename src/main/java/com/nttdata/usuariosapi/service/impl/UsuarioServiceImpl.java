package com.nttdata.usuariosapi.service.impl;

import com.nttdata.usuariosapi.exceptions.CustomException;
import com.nttdata.usuariosapi.exceptions.UsuarioFoundException;
import com.nttdata.usuariosapi.model.Usuario;
import com.nttdata.usuariosapi.repository.UsuarioRepository;
import com.nttdata.usuariosapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;


    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public Usuario guardarUsuario(Usuario usuario) throws Exception {
        Usuario usuarioLocal = usuarioRepository.findByCorreo(usuario.getUsername());
        if (usuarioLocal != null) {
            throw new UsuarioFoundException("El usuario ya esta presente");
        } else {
            usuarioLocal = usuarioRepository.save(usuario);
        }
        return usuarioLocal;
    }

    @Override
    public Usuario obtenerUsuario(String usuarioId) {
        return usuarioRepository.findById(usuarioId).orElse(null);
    }

    @Override
    public void eliminarUsuario(String usuarioId) {
        usuarioRepository.deleteById(usuarioId);

    }

    @Override
    public Set<Usuario> obtenerUsuarios() {
        return new HashSet<>(usuarioRepository.findAll());
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario, String usuarioId) throws CustomException {
        Usuario usuarioLocal = usuarioRepository.findById(usuarioId).orElse(null);

        if (usuarioLocal == null){
            throw new CustomException("User with id: "+ usuarioId + " not found.");
        }

        if (!usuario.getId().equals(usuarioId)) {
            throw new CustomException("Id mismatch. Input object id doesn't match the provided id.");
        }

        usuarioRepository.save(usuario);
        return usuario;
    }
}
