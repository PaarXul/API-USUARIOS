package com.nttdata.usuariosapi.repository;


import com.nttdata.usuariosapi.model.Telefonos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@EnableJpaRepositories
public interface TelefonosRepository extends JpaRepository<Telefonos, Long> {

    Set<Telefonos> findByUsuarioId(String id);
}
