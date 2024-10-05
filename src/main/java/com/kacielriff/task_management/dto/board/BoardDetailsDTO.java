package com.kacielriff.task_management.dto.board;

import com.kacielriff.task_management.dto.member.MemberWithRoleDTO;
import com.kacielriff.task_management.dto.member.SimpleMemberDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDetailsDTO {
    @Schema(description = "ID do board", example = "1")
    private Long id;

    @Schema(description = "Nome do board", example = "Projeto X")
    private String name;

    @Schema(description = "Dono do board")
    private SimpleMemberDTO owner;

    @Schema(description = "Membros do board")
    private List<MemberWithRoleDTO> members;

    @Schema(description = "Data de criação", example = "2024-10-04T09:00:00")
    private LocalDateTime created_at;
}
