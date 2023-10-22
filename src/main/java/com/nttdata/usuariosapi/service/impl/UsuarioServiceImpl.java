package com.nttdata.usuariosapi.service.impl;

import com.nttdata.usuariosapi.config.jwt.JwtUtil;
import com.nttdata.usuariosapi.dto.UsuarioDto;
import com.nttdata.usuariosapi.dto.UsuarioResponseDto;
import com.nttdata.usuariosapi.exceptions.CustomException;
import com.nttdata.usuariosapi.exceptions.UsuarioFoundException;
import com.nttdata.usuariosapi.model.Telefonos;
import com.nttdata.usuariosapi.model.Usuario;
import com.nttdata.usuariosapi.repository.TelefonosRepository;
import com.nttdata.usuariosapi.repository.UsuarioRepository;
import com.nttdata.usuariosapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.PatternMatchUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Value("${security.emailRegex}")
    private String EMAIL_REGEX ;

    @Value("${security.password}")
    private String passwordRegex;
    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final TelefonosRepository telefonosRepository;
    Logger logger = Logger.getLogger(UsuarioServiceImpl.class.getName());

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, JwtUtil jwtUtil, TelefonosRepository telefonosRepository) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
        this.telefonosRepository = telefonosRepository;
    }

    @Override
    @Transactional
    public UsuarioResponseDto guardarUsuario(UsuarioDto usuario) throws Exception, CustomException {
        Usuario usuarioLocal = usuarioRepository.findByCorreo(usuario.getCorreo());
        Usuario usuarioEnviado = new Usuario();
        Usuario usuarioAlmacenado;

        // se setean los valores del usuario enviado al usuario que se va a almacenar
        usuarioEnviado.setNombre(usuario.getNombre());
        usuarioEnviado.setCorreo(usuario.getCorreo());
        usuarioEnviado.setContrasena(usuario.getContrasena());

        if (!validarContrasena(usuarioEnviado.getContrasena())) {
            throw new CustomException("La contraseña no es segura");
        }


        if (validarCorreoElectronico(usuarioEnviado.getCorreo())) {
            throw new CustomException("El correo no es valido");
        }

        if (usuarioLocal != null) {

            throw new CustomException("El Correo ya esta registrado");
        } else {

            String tokenAutentication = jwtUtil.generateToken(usuarioEnviado);

            usuarioEnviado.setToken(tokenAutentication);

            usuarioAlmacenado = usuarioRepository.save(usuarioEnviado);

            // se crea la lista de telefonos
            Set<Telefonos> telefono = new HashSet<>();

            if (usuario.getTelefonos() != null) {
                usuario.getTelefonos().forEach(telefonos -> {
                    // se almacena el usuario en la lista de telefonos y se guarda la lista
                    telefonos.setUsuario(usuarioAlmacenado);
                    telefono.add(telefonos);
                });

                // Se almacena los telefonos del usuario
                telefonosRepository.saveAll(telefono);
            } else {
                throw new CustomException("El usuario debe tener al menos un telefono");
            }
        }
        UsuarioResponseDto usuarioResponseDto = new UsuarioResponseDto();
        usuarioResponseDto.setId(usuarioAlmacenado.getId());
        usuarioResponseDto.setCreado(usuarioAlmacenado.getCreado());
        usuarioResponseDto.setModificado(usuarioAlmacenado.getModificado());
        usuarioResponseDto.setUltimoLogin(usuarioAlmacenado.getUltimoLogin());
        usuarioResponseDto.setToken(usuarioAlmacenado.getToken());
        usuarioResponseDto.setActivo(usuarioAlmacenado.isEnabled());

        return usuarioResponseDto;
    }

    @Override
    public Usuario obtenerUsuario(String usuarioId) throws CustomException {


        if (usuarioId == null) {
            throw new CustomException("El id del usuario no puede ser nulo");
        }
        return usuarioRepository.findById(usuarioId).orElseThrow(() -> new CustomException("El usuario no existe"));
    }

    @Override
    public void eliminarUsuario(String usuarioId) throws CustomException {
        boolean usuarioLocal = usuarioRepository.existsById(usuarioId);

        if (!usuarioLocal) {
            throw new CustomException("El usuario no existe");
        }else {
            usuarioRepository.deleteById(usuarioId);
            throw new CustomException("usuario eliminado correctamente");
        }

    }

    @Override
    public Set<Usuario> obtenerUsuarios() {
        return new HashSet<>(usuarioRepository.findAll());
    }

    @Override
    public UsuarioResponseDto actualizarUsuario(UsuarioDto usuario, String usuarioId) throws CustomException, UsuarioFoundException {
        Usuario usuarioLocal = usuarioRepository.findById(usuarioId).orElseThrow(() -> new CustomException("El usuario no existe"));
        Usuario usuarioAlmacenado;

        // se setean los valores del usuario enviado al usuario que se va a almacenar
        usuarioLocal.setNombre(usuario.getNombre());
        usuarioLocal.setCorreo(usuario.getCorreo());
        usuarioLocal.setContrasena(usuario.getContrasena());
        usuarioLocal.setUltimoLogin(new Date());

        if (!validarContrasena(usuarioLocal.getContrasena())) {
            throw new CustomException("La contraseña no es segura");
        }


        if (validarCorreoElectronico(usuarioLocal.getCorreo())) {
            throw new CustomException("El correo no es valido");
        }

        if (usuarioLocal.getCorreo().equals(usuario.getCorreo())) {
            //Validar si el correo es el mismo que teni el usuario si es asi no se comprueba si existe en otro usuario
            usuarioAlmacenado = usuarioRepository.save(usuarioLocal);

            // se crea la lista de telefonos
            Set<Telefonos> telefono = new HashSet<>();

            if (usuario.getTelefonos() != null) {
                usuario.getTelefonos().forEach(telefonos -> {
                    // se almacena el usuario en la lista de telefonos y se guarda la lista
                    telefonos.setUsuario(usuarioAlmacenado);
                    telefono.add(telefonos);
                });

                // Se almacena los telefonos del usuario
                telefonosRepository.saveAll(telefono);
            } else {
                throw new CustomException("El usuario debe tener al menos un telefono");
            }

        } else {
            usuarioLocal = usuarioRepository.findByCorreo(usuarioLocal.getCorreo());
            //si el usuario cambia el correo se comprueba si el correo existe en otro usuario
            if (usuarioLocal != null) {
                throw new CustomException("El Correo ya esta registrado");
            } else {
                usuarioAlmacenado = usuarioRepository.save(usuarioLocal);

                // se crea la lista de telefonos
                Set<Telefonos> telefono = new HashSet<>();

                if (usuario.getTelefonos() != null) {
                    usuario.getTelefonos().forEach(telefonos -> {
                        // se almacena el usuario en la lista de telefonos y se guarda la lista
                        telefonos.setUsuario(usuarioAlmacenado);
                        telefono.add(telefonos);
                    });

                    // Se almacena los telefonos del usuario
                    telefonosRepository.saveAll(telefono);
                } else {
                    throw new CustomException("El usuario debe tener al menos un telefono");
                }

            }
        }

        UsuarioResponseDto usuarioResponseDto = new UsuarioResponseDto();
        usuarioResponseDto.setId(usuarioAlmacenado.getId());
        usuarioResponseDto.setCreado(usuarioAlmacenado.getCreado());
        usuarioResponseDto.setModificado(usuarioAlmacenado.getModificado());
        usuarioResponseDto.setUltimoLogin(usuarioAlmacenado.getUltimoLogin());
        usuarioResponseDto.setToken(usuarioResponseDto.getToken());
        usuarioResponseDto.setActivo(usuarioAlmacenado.isEnabled());

        return usuarioResponseDto;
    }

    public boolean validarCorreoElectronico(String correo) {
        return PatternMatchUtils.simpleMatch(EMAIL_REGEX, correo);
    }


    public boolean validarContrasena(String contrasena) {
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(contrasena);
        return matcher.matches();
    }


}