package com.example.demo.service;

import com.example.demo.dto.UsuarioRegisterDTO;
import com.example.demo.model.Rol;
import com.example.demo.model.UserAuthority;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Usuario> findByUsername(String username) {
        return this.repository.findByUsername(username);
    }

    public Usuario save(UsuarioRegisterDTO usuarioDTO) {
        String pass = usuarioDTO.password();
        Rol rol;
        if (pass.equals("admin"))
            rol = Rol.ROLE_ADMIN;
        else if (pass.equals("itic"))
            rol = Rol.ROLE_PROFESOR;
        else
            rol = Rol.ROLE_ALUMNO;
        System.out.println("este es el rol:" + rol);


        Usuario user = new Usuario(
                null,
                null,
                usuarioDTO.username(),
                passwordEncoder.encode(pass),
                usuarioDTO.email(),
                List.of(UserAuthority.READ),
                rol

        );
        return this.repository.save(user);
    }

}
