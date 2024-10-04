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
public class MemberWithRoleDTO extends SimpleMemberDTO {
    
    @Schema(description = "Role do usuário", example = "viewer")
    private String role;
}
