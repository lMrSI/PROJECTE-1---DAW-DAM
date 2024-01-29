package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.UsuarioRegisterDTO;
import com.example.demo.model.Usuario;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*") @RestController @RequestMapping("/auth")
@Tag(name="Usuarios", description="Peticiones de usuario")
public class UsuarioAuthController {

    @Autowired
    private UsuarioService userService;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Operation(summary = "registrar", description = "register")
    @SecurityRequirements
    @PostMapping("/register")
    public Usuario save(@RequestBody UsuarioRegisterDTO userDTO){
        return this.userService.save(userDTO);
    }

    @Operation(summary = "Autentificar", description = "login")
    @SecurityRequirements
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginDTO){
        Authentication authDTO = new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());

        Authentication authentication = this.authManager.authenticate(authDTO);
        Usuario user = (Usuario) authentication.getPrincipal();

        String token = this.jwtTokenProvider.generateToken(authentication);

        return new LoginResponse(
                user.getUsername(),
                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(),
                token);
    }
}
