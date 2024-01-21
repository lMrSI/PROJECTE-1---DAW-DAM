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
        UserAuthority ROLE;
        if (pass.equals("admin"))
            ROLE = UserAuthority.ROLE_ADMIN;
        else if (pass.equals("profe"))
            ROLE = UserAuthority.ROLE_PROFE;
        else if (pass.equals("empresa"))
            ROLE = UserAuthority.ROLE_EMPRESA;
        else
            ROLE = UserAuthority.ROLE_ALUMNE;


        Usuario user = new Usuario(
                null,
                null,
                usuarioDTO.username(),
                passwordEncoder.encode(pass),
                usuarioDTO.email(),
                List.of(UserAuthority.READ, UserAuthority.WRITE, ROLE)
        );
        return this.repository.save(user);
    }

}
