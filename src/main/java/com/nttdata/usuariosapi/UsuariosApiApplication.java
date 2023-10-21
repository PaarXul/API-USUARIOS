package com.nttdata.usuariosapi;

import com.nttdata.usuariosapi.exceptions.CustomException;
import com.nttdata.usuariosapi.exceptions.UsuarioFoundException;
import com.nttdata.usuariosapi.model.Telefonos;
import com.nttdata.usuariosapi.model.Usuario;
import com.nttdata.usuariosapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class UsuariosApiApplication implements CommandLineRunner {

      // Insercion de usuarios
           @Autowired
           private UsuarioService usuarioService;

		   @Autowired
           private BCryptPasswordEncoder bCryptPasswordEncoder;


	public static void main(String[] args) {
		SpringApplication.run(UsuariosApiApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {


//Insercion de Usuarios
        try {
            Usuario usuario = new Usuario();

            usuario.setUsername("admin");
			usuario.setCorreo("admin@gmail.com");
            usuario.setPassword(bCryptPasswordEncoder.encode("12345"));

			Telefonos telefonos = new Telefonos();
			telefonos.setNumero("12345678");
			telefonos.setCodigoCiudad("1");
			telefonos.setCodigoPais("57");

			Set<Telefonos> telefonosSet = new HashSet<>();
			telefonosSet.add(telefonos);

			usuario.setTelefonos(telefonosSet);

            Usuario usuario2 = new Usuario();
            usuario2.setUsername("Normal");
			usuario2.setCorreo("normal@gmail.com");
			usuario2.setPassword(bCryptPasswordEncoder.encode("12345"));

			Telefonos telefonos2 = new Telefonos();
			telefonos2.setNumero("12345678");
			telefonos2.setCodigoCiudad("1");
			telefonos2.setCodigoPais("57");

			Set<Telefonos> telefonosSet2 = new HashSet<>();
			telefonosSet2.add(telefonos2);

			usuario2.setTelefonos(telefonosSet2);

			Usuario usuarioGuardado = usuarioService.guardarUsuario(usuario);

            Usuario usuarioGuardados = usuarioService.guardarUsuario(usuario2);

        } catch (UsuarioFoundException exception) {
            exception.printStackTrace();
        } catch (CustomException e) {
            throw new RuntimeException(e);
        }


    }
}
