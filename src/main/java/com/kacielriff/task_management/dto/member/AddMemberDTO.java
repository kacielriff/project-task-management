package com.kacielriff.task_management.dto.member;

import com.kacielriff.task_management.entity.enums.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddMemberDTO {

    @NotNull(message = "Campo obrigatório")
    @NotBlank(message = "Campo obrigatório")
    @Positive(message = "ID deve ser um número inteiro positivo válido")
    private Long id;

    @NotNull(message = "Campo obrigatório")
    @NotBlank(message = "Campo obrigatório")
    private MemberRole role;
}
