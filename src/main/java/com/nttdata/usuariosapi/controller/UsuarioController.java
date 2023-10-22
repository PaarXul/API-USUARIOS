package com.nttdata.usuariosapi.controller;

import com.nttdata.usuariosapi.dto.UsuarioDto;
import com.nttdata.usuariosapi.dto.UsuarioResponseDto;
import com.nttdata.usuariosapi.exceptions.CustomException;
import com.nttdata.usuariosapi.model.Usuario;
import com.nttdata.usuariosapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/")
    public ResponseEntity<UsuarioResponseDto> guardarUsuario(@RequestBody UsuarioDto usuario) throws Exception, CustomException {
        return ResponseEntity.ok(usuarioService.guardarUsuario(usuario));
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("usuarioId") String usuarioId) throws CustomException {
        return ResponseEntity.ok(usuarioService.obtenerUsuario(usuarioId));
    }

    @DeleteMapping("/{usuarioId}")
    public void eliminarUsuario(@PathVariable("usuarioId") String usuarioId) throws CustomException {
        usuarioService.eliminarUsuario(usuarioId);
        new CustomException("Usuario eliminado correctamente: "+usuarioId);

    }

    @GetMapping("/todos/")
    public ResponseEntity<Set<Usuario>> obtenerUsuarios() {
        return ResponseEntity.ok(usuarioService.obtenerUsuarios());
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponseDto> actualizarUsuario(@PathVariable("usuarioId") String usuarioId, @RequestBody UsuarioDto usuario) throws CustomException, Exception {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(usuario, usuarioId));
    }

}