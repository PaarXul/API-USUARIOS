package com.nttdata.usuariosapi.service.impl;

import com.nttdata.usuariosapi.exceptions.CustomException;
import com.nttdata.usuariosapi.exceptions.UsuarioFoundException;
import com.nttdata.usuariosapi.model.Telefonos;
import com.nttdata.usuariosapi.model.Usuario;
import com.nttdata.usuariosapi.repository.TelefonosRepository;
import com.nttdata.usuariosapi.repository.UsuarioRepository;
import com.nttdata.usuariosapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TelefonosRepository telefonosRepository;

    Logger logger = Logger.getLogger(UsuarioServiceImpl.class.getName());

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, TelefonosRepository telefonosRepository) {
        this.usuarioRepository = usuarioRepository;
        this.telefonosRepository = telefonosRepository;
    }

    @Override
    @Transactional
    public Usuario guardarUsuario(Usuario usuario) throws Exception, CustomException {
        Usuario usuarioLocal = usuarioRepository.findByCorreo(usuario.getCorreo());
        Usuario usuarioAlmacenado ;

        if (usuarioLocal != null) {

            throw new UsuarioFoundException("El usuario ya esta presente");
        } else {
            usuario.setId(UUID.randomUUID().toString());
            usuarioAlmacenado= usuarioRepository.save(usuario);
            Set<Telefonos> telefono = new HashSet<>();
            logger.info("Usuario: " + usuarioAlmacenado.getNombre());


            if (usuario.getTelefonos() != null) {
                usuario.getTelefonos().forEach(telefonos -> {

                    telefonos.setUsuario(usuarioAlmacenado);

                    logger.info("Telefono: " + telefonos.getNumero());
                    logger.info("Usuario: " + telefonos.getUsuario());
                    telefono.add(telefonos);
                });

                telefonosRepository.saveAll(telefono);

                logger.info("Telefono guardado con exito");


            } else {
                throw new CustomException("El usuario debe tener al menos un telefono");
            }


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
