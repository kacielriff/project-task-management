package com.kacielriff.task_management.entity;

import com.kacielriff.task_management.entity.pk.CardLabelPK;
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
@Table(name = "card_labels")
public class CardLabel {

    @EmbeddedId
    private CardLabelPK id;

    @ManyToOne
    @MapsId("cardId")
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @ManyToOne
    @MapsId("labelId")
    @JoinColumn(name = "label_id", nullable = false)
    private Label label;
}