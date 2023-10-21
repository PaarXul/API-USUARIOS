package com.nttdata.usuariosapi.controller;


import com.nttdata.usuariosapi.exceptions.CustomException;
import com.nttdata.usuariosapi.model.Usuario;
import com.nttdata.usuariosapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

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
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario) throws Exception, CustomException {
        return ResponseEntity.ok(usuarioService.guardarUsuario(usuario));
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("usuarioId") String usuarioId) {
        return ResponseEntity.ok(usuarioService.obtenerUsuario(usuarioId));
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable("usuarioId") String usuarioId) {
        usuarioService.eliminarUsuario(usuarioId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/todos/")
    public ResponseEntity<Set<Usuario>> obtenerUsuarios() {
        return ResponseEntity.ok(usuarioService.obtenerUsuarios());
    }

    @PatchMapping("/{usuarioId}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable("usuarioId") String usuarioId, @RequestBody Usuario usuario) throws CustomException, Exception {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(usuario, usuarioId));
    }

}
