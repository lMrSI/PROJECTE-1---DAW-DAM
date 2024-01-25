package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity @Table(name="usuarios")
public class Usuario implements UserDetails {
    //P R O P I E D A D E S
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "usuarios")
    private List<Oferta> ofertas;
    @Schema(example = "1", description = "Identificador de usuario")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    @Schema(example = "alumno", description = "Nombre de usuario")
    private String username;
    @Schema(example = "alumno", description = "Contrasenya")
    private String password;
    @Schema(example = "alumno@iticbcn.cat", description = "correo")
    private String email;
    @ElementCollection(fetch = FetchType.EAGER) @Enumerated(EnumType.STRING)
    private List<UserAuthority> authorities = new ArrayList<>();


    //C O N S T R U C T O R
    public Usuario() {
    }
    public Usuario(List<Oferta> ofertas, Long idUsuario, String username, String password, String email, List<UserAuthority> authorities) {
        this.ofertas = ofertas;
        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
    }
    public <E> Usuario(Object o, String username, String encode, String email, List<E> read) {
    }

    //G E T T E R S   I   S E T T E R S
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.toString()))
                .toList();
    }
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    public Long getIdUsuario() {
        return idUsuario;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public List<Oferta> getOfertas() {
        return ofertas;
    }
    public void setOfertas(List<Oferta> ofertas) {
        this.ofertas = ofertas;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


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
    public boolean isEnabled() {
        return true;
    }

}
