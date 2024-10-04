package com.kacielriff.task_management.dto.shared;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T> {
    @Schema(description = "Total de elementos da página.", example = "9")
    private Long totalElementos;

    @Schema(description = "Total de páginas.", example = "1")
    private Integer totalPages;

    @Schema(description = "Página atual.", example = "1")
    private Integer page;

    @Schema(description = "Número de registros solicitados por página.", example = "10")
    private Integer size;

    @Schema(description = "Lista de registros da página.")
    private List<?> content;
}