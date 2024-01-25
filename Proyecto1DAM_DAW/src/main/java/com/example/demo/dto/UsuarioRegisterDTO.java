package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UsuarioRegisterDTO(@Schema(example = "alumno") String username,
                                 @Schema(example = "alumno@iticbcn.cat") String email,
                                 @Schema(example = "alumno") String password,
                                 @Schema(example = "alumno") String password2) {
}
