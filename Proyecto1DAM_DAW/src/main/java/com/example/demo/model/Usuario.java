package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="usuarios")
public class Usuario implements UserDetails {


    /////////////////
    /////////////
    ///////////
    //DEBE SER DE MANY TO MANY
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idOferta")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties("usuarios")
    private Oferta oferta;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<UserAuthority> authorities = new ArrayList<>();


    public Usuario() {
    }

    public Usuario(Oferta oferta, Long idUsuario, String username, String password, String email, List<UserAuthority> authorities) {
        this.oferta = oferta;
        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
    }

    public <E> Usuario(Object o, String username, String encode, String email, List<E> read) {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.toString())).toList();
    }

    public Oferta getOferta() {
        return oferta;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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

    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getEmail() {
        return email;
    }
}
