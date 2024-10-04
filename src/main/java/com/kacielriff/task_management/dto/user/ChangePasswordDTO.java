package com.kacielriff.task_management.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDTO {

    @NotNull(message = "Campo obrigatório")
    @NotBlank(message = "Campo obrigatório")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#^()~])[A-Za-z\\d@$!%*?&#^()~]{8,32}$",
            message = "Senha inválida. A senha deve ter entre 8 e 32 caracteres, conter letras  maiúsculas, minúsculas, números e caracteres especiais."
    )
    @Schema(description = "Senha do usuário")
    private String password;
}
