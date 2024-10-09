package com.kacielriff.task_management.dto.list;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateListDTO {
    @NotNull(message = "Campo obrigatório")
    @NotBlank(message = "Campo obrigatório")
    @Size(min = 3, max = 255, message = "Nome deve ter no mínimo 3 caracteres e no máximo 255 caracteres")
    @Schema(description = "Nome do board", example = "Projeto X")
    private String name;

    @NotNull(message = "Campo obrigatório")
    @NotBlank(message = "Campo obrigatório")
    @Positive(message = "Posição deve ser um número valido positivo")
    private Integer position;

    @NotNull(message = "Campo obrigatório")
    @NotBlank(message = "Campo obrigatório")
    @Positive(message = "Board ID deve ser um número valido positivo")
    private Long boardId;
}
