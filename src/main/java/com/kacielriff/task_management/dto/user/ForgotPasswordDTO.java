package com.kacielriff.task_management.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordDTO {
    @NotNull(message = "Campo obrigatório")
    @NotBlank(message = "Campo obrigatório")
    @Email(message = "Campo deve conter um email válido")
    @Schema(description = "Email do usuário", example = "email@email.com")
    private String email;
}
