package com.nttdata.usuariosapi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario  {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nombre;

    @Column(unique = true)
    private String correo;
    private String contrasena;
    private boolean enabled = true;

    private String token;

    private Date creado;
    private Date modificado;
    private Date ultimoLogin;


    @OneToMany(mappedBy = "usuario",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonManagedReference
    @Fetch(FetchMode.JOIN)
    private Set<Telefonos> telefonos;

    @PrePersist
    protected void onCreate() {
        creado = new Date();
        modificado = creado;
    }

    @PreUpdate
    protected void onUpdate() {
        modificado = new Date();
    }

}