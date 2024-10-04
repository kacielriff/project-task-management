package com.kacielriff.task_management.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {

    @NotNull(message = "Campo obrigatório")
    @NotBlank(message = "Campo obrigatório")
    @Size(min = 3, max = 255, message = "Nome deve ter no mínimo 3 caracteres e no máximo 255 caracteres")
    @Schema(description = "Nome do usuário", example = "Fulano da Silva")
    private String name;

    @NotNull(message = "Campo obrigatório")
    @NotBlank(message = "Campo obrigatório")
    @Email(message = "Campo deve conter um email válido")
    @Schema(description = "Email do usuário", example = "email@email.com")
    private String email;

    @NotNull(message = "Campo obrigatório")
    @NotBlank(message = "Campo obrigatório")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#^()~])[A-Za-z\\d@$!%*?&#^()~]{8,32}$",
            message = "Senha inválida. A senha deve ter entre 8 e 32 caracteres, conter letras  maiúsculas, minúsculas, números e caracteres especiais."
    )
    @Schema(description = "Senha do usuário")
    private String password;

    @Schema(description = "URL da imagem de perfil do usuário", example = "https://firebasestorage.googleapis.com/v0/b/task-management-d64a6.appspot.com/o/imagem-gato-cs.jpg?alt=media&token=28a8cb80-9115-465b-852d-f1b2157e5afc")
    private String image;
}
