package com.kacielriff.task_management.dto.list;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleListDTO {
    @Schema(description = "ID da lista", example = "1")
    private Long id;

    @Schema(description = "Nome da lista", example = "To Do")
    private String name;

    @Schema(description = "Posição da lista", example = "0")
    private Integer position;
}
