package com.kacielriff.task_management.entity.pk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CardLabelPK implements Serializable {

    @Column(name = "card_id")
    private Long cardId;

    @Column(name = "label_id")
    private Long labelId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardLabelPK)) return false;
        CardLabelPK that = (CardLabelPK) o;
        return Objects.equals(cardId, that.cardId) &&
                Objects.equals(labelId, that.labelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardId, labelId);
    }
}
