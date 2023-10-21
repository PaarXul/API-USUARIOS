package com.nttdata.usuariosapi.repository;

import com.nttdata.usuariosapi.model.Usuario;
import org.hibernate.annotations.SQLSelect;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Usuario findByCorreo(String correo);
    @NotNull Optional<Usuario> findById(String usuarioId);
    void deleteById (String usuarioId);
}
