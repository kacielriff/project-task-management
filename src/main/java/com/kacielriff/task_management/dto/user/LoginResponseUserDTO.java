package com.kacielriff.task_management.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseUserDTO {

    @Schema(description = "ID do usuário", example = "1")
    private Long id;

    @Schema(description = "Email do usuário", example = "email@email.com")
    private String email;

    @Schema(description = "Token de autenticação do usuário", example = "eyJhbGciOc6IkCJ9.eyJzdWIiOM0tZSI6.SflKxwRJSMeKQT4fw")
    private String token;
}
