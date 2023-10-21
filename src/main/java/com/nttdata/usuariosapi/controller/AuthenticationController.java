package com.nttdata.usuariosapi.controller;

import com.nttdata.usuariosapi.config.jwt.JwtRequest;
import com.nttdata.usuariosapi.config.jwt.JwtResponse;
import com.nttdata.usuariosapi.config.jwt.JwtUtil;
import com.nttdata.usuariosapi.exceptions.UsuarioNotFoundException;
import com.nttdata.usuariosapi.model.Usuario;
import com.nttdata.usuariosapi.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin("*") // Para evitar problemas de CORS
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtils;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception{
        try{
            autenticar(jwtRequest.getUsername(),jwtRequest.getPassword());

        }catch (UsuarioNotFoundException exception){
            exception.printStackTrace();
            throw new Exception("Error en el username o password");
        }

        UserDetails userDetails =  this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void autenticar(String username, String password) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (DisabledException disabledException){
            throw new Exception("USER_DISABLED: "+ disabledException.getMessage());
        }catch (BadCredentialsException badCredentialsException){
            throw new Exception("INVALID_CREDENTIALS: "+ badCredentialsException.getMessage());
        }
    }

    @GetMapping("/actual-usuario")
    public Usuario obtenerUsuarioActual(Principal principal){
        return (Usuario) this.userDetailsService.loadUserByUsername(principal.getName());
    }
}