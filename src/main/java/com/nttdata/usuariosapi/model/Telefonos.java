package com.nttdata.usuariosapi.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "telefonos")
public class Telefonos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private String codigoCiudad;
    private String codigoPais;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario usuario;
}
