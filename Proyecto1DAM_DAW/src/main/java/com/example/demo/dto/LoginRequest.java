package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequest(@Schema(example = "alumno") String username, @Schema(example = "alumno") String password) {
}
