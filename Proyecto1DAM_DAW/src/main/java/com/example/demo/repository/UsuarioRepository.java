package com.example.demo.repository;

import com.example.demo.model.Oferta;
import com.example.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(String username);
    Usuario findByIdUsuario(Long idUsuario);
    List<Usuario> findAll();
}
