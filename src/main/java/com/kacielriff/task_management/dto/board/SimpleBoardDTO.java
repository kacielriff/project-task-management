package com.kacielriff.task_management.dto.board;

import com.kacielriff.task_management.entity.enums.MemberRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleBoardDTO {

    @Schema(description = "ID do board", example = "1")
    private Long id;

    @Schema(description = "Nome do board", example = "Projeto X")
    private String name;

    @Schema(description = "Role do usuário logado")
    private MemberRole role;

    @Schema(description = "Data de criação", example = "2024-10-04T09:00:00")
    private LocalDateTime created_at;
}
