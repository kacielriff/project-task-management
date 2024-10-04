package com.kacielriff.task_management.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum MemberRole {
    OWNER(1),
    MEMBER(2),
    VIEWER(3);

    private Integer type;

    public static MemberRole ofType(Integer type) {
        return Arrays.stream(MemberRole.values())
                .filter(t -> t.getType().equals(type))
                .findFirst()
                .get();
    }
}
