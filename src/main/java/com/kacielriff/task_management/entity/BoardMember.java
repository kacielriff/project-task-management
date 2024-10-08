package com.kacielriff.task_management.entity;

import com.kacielriff.task_management.entity.enums.MemberRole;
import com.kacielriff.task_management.entity.pk.BoardMemberPK;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "board_members")
public class BoardMember {

    @EmbeddedId
    private BoardMemberPK id;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("boardId")
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role", nullable = false)
    private MemberRole role;
}
