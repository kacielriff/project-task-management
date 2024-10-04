package com.kacielriff.task_management.dto.shared;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    @Schema(description = "Mensagem de execução de alguma operação", example = "Operação executada com sucesso")
    private String message;
}
