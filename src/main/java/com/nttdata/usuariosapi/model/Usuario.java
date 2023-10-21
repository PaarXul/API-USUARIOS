package com.nttdata.usuariosapi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nttdata.usuariosapi.config.jwt.Authority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario  implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nombre;

    @Column(unique = true)
    private String correo;
    private String contrasena;

    private String username;
    private String password;

    private boolean enabled = true;

    @Getter
    @OneToMany(mappedBy = "usuario",fetch = FetchType.EAGER)
    @JsonIgnore
    @Fetch(FetchMode.JOIN)
    private Set<Telefonos> telefonos;


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> autoridades = new HashSet<>();

            autoridades.add(new Authority("NORMAL"));
        return autoridades;
    }



}
