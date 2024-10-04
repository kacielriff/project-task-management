package com.kacielriff.task_management.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleMemberDTO {
    @Schema(description = "ID do board", example = "1")
    private Long id;

    @Schema(description = "Nome do board", example = "Projeto X")
    private String name;
}
