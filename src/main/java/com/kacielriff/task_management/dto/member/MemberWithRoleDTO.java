package com.kacielriff.task_management.dto.member;

import com.kacielriff.task_management.entity.enums.MemberRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberWithRoleDTO {
    @Schema(description = "ID do membro", example = "1")
    private Long id;

    @Schema(description = "Nome do membro", example = "Fulano da Silva")
    private String name;
    
    @Schema(description = "Role do membro")
    private MemberRole role;
}
